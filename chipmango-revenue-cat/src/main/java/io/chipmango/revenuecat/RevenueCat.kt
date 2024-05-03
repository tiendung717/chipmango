package io.chipmango.revenuecat

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.revenuecat.purchases.CustomerInfo
import io.chipmango.revenuecat.extensions.hasActiveEntitlements
import com.revenuecat.purchases.LogLevel
import com.revenuecat.purchases.Offering
import com.revenuecat.purchases.Package
import com.revenuecat.purchases.PeriodType
import com.revenuecat.purchases.PurchaseParams
import com.revenuecat.purchases.Purchases
import com.revenuecat.purchases.PurchasesConfiguration
import com.revenuecat.purchases.PurchasesError
import com.revenuecat.purchases.getOfferingsWith
import com.revenuecat.purchases.interfaces.GetStoreProductsCallback
import com.revenuecat.purchases.interfaces.ReceiveCustomerInfoCallback
import com.revenuecat.purchases.models.StoreProduct
import com.revenuecat.purchases.purchaseWith
import com.revenuecat.purchases.restorePurchasesWith
import dagger.hilt.android.qualifiers.ApplicationContext
import io.chipmango.revenuecat.receiver.DiscountReceiver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.time.Duration
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.max

@Singleton
class RevenueCat @Inject constructor(@ApplicationContext private val context: Context) {

    companion object {
        private const val KEY_APP_FIRST_LAUNCH = "key_app_first_launch"
        private const val KEY_PREMIUM = "key_premium"
        private const val KEY_DISCOUNT_REMINDER = "key_discount_reminder"
        private const val KEY_DISCOUNT_EXPIRY = "key_discount_expiry"
    }

    private val Context.dataStore by preferencesDataStore(name = "purchase.ds")
    private val coroutineScope = CoroutineScope(Dispatchers.Default)
    private var isPaywallOnboardingShown = false

    fun init(context: Context, sdkKey: String, logEnabled: Boolean) {
        if (logEnabled) {
            Purchases.logLevel = LogLevel.DEBUG
        }
        Purchases.configure(PurchasesConfiguration.Builder(context, sdkKey).build())
    }

    suspend fun verifyInitialAppLaunch(onSubsequentLaunch: () -> Unit) {
        val isAppLaunchedFirstTime = runBlocking {
            read(KEY_APP_FIRST_LAUNCH, true).first()
        }
        if (!isAppLaunchedFirstTime) {
            onSubsequentLaunch()
        } else {
            save(KEY_APP_FIRST_LAUNCH, false)
        }
    }


    fun setPaywallOnboardingShown() {
        isPaywallOnboardingShown = true
    }

    fun isPaywallOnboardingShown(): Boolean {
        return isPaywallOnboardingShown
    }

    fun fetchCurrentOffer(
        onError: (String) -> Unit,
        onSuccess: (Offering?) -> Unit
    ) {
        Purchases.sharedInstance.getOfferingsWith(
            onError = { error ->
                onError(error.message)
            },
            onSuccess = { offerings ->
                onSuccess(offerings.current)
            }
        )
    }

    fun fetchProducts(
        productIds: List<String>,
        onError: (String) -> Unit,
        onGetStoreProducts: (List<StoreProduct>) -> Unit
    ) {
        Purchases.sharedInstance.getProducts(
            productIds = productIds,
            callback = object : GetStoreProductsCallback {
                override fun onError(error: PurchasesError) {
                    onError(error.message)
                }

                override fun onReceived(storeProducts: List<StoreProduct>) {
                    onGetStoreProducts(storeProducts)
                }
            }
        )
    }

    suspend fun makePurchase(
        activity: Activity,
        packageToPurchase: Package,
        purchaseListener: PurchaseListener? = null
    ) {
        Purchases.sharedInstance.purchaseWith(
            PurchaseParams.Builder(activity, packageToPurchase).build(),
            onError = { error, userCancelled ->
                if (userCancelled) {
                    purchaseListener?.onPurchaseCancelled()
                } else {
                    purchaseListener?.onPurchaseError(error)
                }
            },
            onSuccess = { _, customerInfo ->
                savePurchase(customerInfo)
                purchaseListener?.onPurchaseCompleted(customerInfo)
            }
        )
    }

    suspend fun makePurchase(
        activity: Activity,
        product: StoreProduct,
        purchaseListener: PurchaseListener? = null
    ) {
        Purchases.sharedInstance.purchaseWith(
            PurchaseParams.Builder(activity, product).build(),
            onError = { error, userCancelled ->
                if (userCancelled) {
                    purchaseListener?.onPurchaseCancelled()
                } else {
                    purchaseListener?.onPurchaseError(error)
                }
            },
            onSuccess = { _, customerInfo ->
                val isPremium = customerInfo.entitlements.hasActiveEntitlements()
                coroutineScope.launch {
                    save(KEY_PREMIUM, isPremium)
                }.invokeOnCompletion {
                    purchaseListener?.onPurchaseCompleted(customerInfo)
                }
            }
        )
    }

    fun restorePurchase(purchaseListener: PurchaseListener? = null) {
        Purchases.sharedInstance.restorePurchasesWith(
            onError = { error ->
                purchaseListener?.onPurchaseError(error)
            },
            onSuccess = { customerInfo ->
                val isPremium = customerInfo.entitlements.hasActiveEntitlements()
                coroutineScope.launch {
                    save(KEY_PREMIUM, isPremium)
                }.invokeOnCompletion {
                    purchaseListener?.onPurchaseCompleted(customerInfo)
                }
            }
        )
    }

    fun cancelSubscription(
        openManagementUri: (Uri) -> Unit,
        onNoActiveSubscription: () -> Unit,
        onError: (String) -> Unit
    ) {
        Purchases.sharedInstance.getCustomerInfo(
            object : ReceiveCustomerInfoCallback {
                override fun onError(error: PurchasesError) {
                    onError(error.message)
                }

                override fun onReceived(customerInfo: CustomerInfo) {
                    val managementUrl = customerInfo.managementURL
                    if (managementUrl == null) {
                        onNoActiveSubscription()
                        return
                    }
                    openManagementUri(managementUrl)
                }
            }
        )
    }

    fun hasUserCancelledTrial(customerInfo: CustomerInfo): Boolean {
        val hasActiveEntitlement = customerInfo.entitlements.hasActiveEntitlements()
        val hasCancelledTrial = customerInfo.entitlements.all.any { entry ->
            val entitlementInfo = entry.value
            val cancelledTrial =
                entitlementInfo.isActive.not() && entitlementInfo.periodType == PeriodType.TRIAL
            cancelledTrial
        }

        return hasCancelledTrial && !hasActiveEntitlement
    }

    fun hasUsedAppForDuration(customerInfo: CustomerInfo, duration: Duration): Boolean {
        val hasActiveEntitlement = customerInfo.entitlements.hasActiveEntitlements()
        val firstSeen = customerInfo.firstSeen.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
        val now = LocalDateTime.now()
        val appUsageDuration = Duration.between(firstSeen, now)
        return appUsageDuration >= duration && !hasActiveEntitlement
    }

    fun evaluateDiscountOfferDisplay(
        discountStartTime: ZonedDateTime,
        discountUniqueRequestId: Int,
        discountTitle: String,
        discountMessage: String,
        discountDuration: Duration,
        discountReceiverClass: Class<out DiscountReceiver>,
        shouldTriggerDiscount: (CustomerInfo) -> Boolean = { hasUserCancelledTrial(it) }
    ) {
        if (!isDiscountReminderSet()) {
            Purchases.sharedInstance.getCustomerInfo(
                object : ReceiveCustomerInfoCallback {
                    override fun onError(error: PurchasesError) {

                    }

                    override fun onReceived(customerInfo: CustomerInfo) {
                        if (shouldTriggerDiscount(customerInfo)) {
                            setDiscountReminder(
                                discountStartTime,
                                discountUniqueRequestId,
                                discountTitle,
                                discountMessage,
                                discountReceiverClass
                            )
                            onDiscountReminderSetupComplete()
                            onDiscountExpirySet(discountStartTime, discountDuration)
                        }
                    }
                }
            )
        }
    }

    private fun setDiscountReminder(
        discountStartTime: ZonedDateTime,
        discountUniqueRequestId: Int,
        discountTitle: String,
        discountMessage: String,
        discountReceiverClass: Class<out DiscountReceiver>
    ) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val discountIntent = Intent(context, discountReceiverClass).apply {
            putExtra("message", discountMessage)
            putExtra("title", discountTitle)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            discountUniqueRequestId,
            discountIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val startTimeMillis = discountStartTime.toInstant().toEpochMilli()

        val canScheduleExactAlarms = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            alarmManager.canScheduleExactAlarms()
        } else {
            true
        }
        if (canScheduleExactAlarms) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, startTimeMillis, pendingIntent)
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, startTimeMillis, pendingIntent)
        }
    }

    fun isDiscountExpired(): Boolean {
        val expiry = runBlocking {
            read(KEY_DISCOUNT_EXPIRY, 0L).first()
        }
        return expiry <= System.currentTimeMillis()
    }

    fun getRemainingDiscountDuration(): Duration {
        val expiry = runBlocking {
            read(KEY_DISCOUNT_EXPIRY, 0L).first()
        }
        return Duration.ofMillis(max(0L, expiry - System.currentTimeMillis()))
    }

    fun getPremiumStatusFlow(): Flow<Boolean> {
        return read(KEY_PREMIUM, false)
    }

    private suspend fun save(key: String, value: Boolean) {
        context.dataStore.edit { settings ->
            val prefKey = booleanPreferencesKey(key)
            settings[prefKey] = value
        }
    }

    private suspend fun save(key: String, value: Long) {
        context.dataStore.edit { settings ->
            val prefKey = longPreferencesKey(key)
            settings[prefKey] = value
        }
    }

    private fun read(key: String, defaultValue: Boolean): Flow<Boolean> {
        val prefKey = booleanPreferencesKey(key)
        return context.dataStore.data.map { it[prefKey] ?: defaultValue }
    }

    private fun read(key: String, defaultValue: Long): Flow<Long> {
        val prefKey = longPreferencesKey(key)
        return context.dataStore.data.map { it[prefKey] ?: defaultValue }
    }

    private fun savePurchase(customerInfo: CustomerInfo) {
        coroutineScope.launch {
            save(KEY_PREMIUM, customerInfo.entitlements.hasActiveEntitlements())
        }
    }

    private fun onDiscountReminderSetupComplete() {
        coroutineScope.launch {
            save(KEY_DISCOUNT_REMINDER, true)
        }
    }

    private fun isDiscountReminderSet(): Boolean {
        return runBlocking {
            read(KEY_DISCOUNT_REMINDER, false).first()
        }
    }

    private fun onDiscountExpirySet(discountStartTime: ZonedDateTime, discountDuration: Duration) {
        coroutineScope.launch {
            save(KEY_DISCOUNT_EXPIRY, discountStartTime.toInstant().toEpochMilli() + discountDuration.toMillis())
        }
    }
}