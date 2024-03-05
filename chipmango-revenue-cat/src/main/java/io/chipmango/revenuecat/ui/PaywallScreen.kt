package io.chipmango.revenuecat.ui

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.revenuecat.purchases.CustomerInfo
import com.revenuecat.purchases.PurchasesError
import com.revenuecat.purchases.models.StoreTransaction
import com.revenuecat.purchases.ui.revenuecatui.ExperimentalPreviewRevenueCatUIPurchasesAPI
import com.revenuecat.purchases.ui.revenuecatui.Paywall
import com.revenuecat.purchases.ui.revenuecatui.PaywallListener
import com.revenuecat.purchases.ui.revenuecatui.PaywallOptions
import io.chipmango.revenuecat.extensions.hasActiveEntitlements
import io.chipmango.revenuecat.viewmodel.RcViewModel

@OptIn(ExperimentalPreviewRevenueCatUIPurchasesAPI::class)
@Composable
fun PaywallScreen(
    rcViewModel: RcViewModel = hiltViewModel(),
    onDismiss: () -> Unit,
    onPurchaseCompleted: (Boolean) -> Unit
) {
    Paywall(
        options = PaywallOptions.Builder(onDismiss)
            .setListener(
                object : PaywallListener {
                    override fun onPurchaseCompleted(customerInfo: CustomerInfo, storeTransaction: StoreTransaction) {
                        rcViewModel.savePurchase(customerInfo)
                        val isPremium = customerInfo.entitlements.hasActiveEntitlements()
                        onPurchaseCompleted(isPremium)
                    }

                    override fun onRestoreCompleted(customerInfo: CustomerInfo) {
                        rcViewModel.savePurchase(customerInfo)
                        val isPremium = customerInfo.entitlements.hasActiveEntitlements()
                        onPurchaseCompleted(isPremium)
                    }
                }
            )
            .build()
    )
}