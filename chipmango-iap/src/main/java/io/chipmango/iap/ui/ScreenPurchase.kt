package io.chipmango.iap.ui

import android.app.Activity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.billingclient.api.ProductDetails
import io.chipmango.iap.R
import io.chipmango.iap.ext.WrappedProduct
import io.chipmango.iap.model.PremiumFeature
import io.chipmango.iap.viewmodel.PurchaseViewModel
import kotlinx.coroutines.launch


@Composable
internal fun ScreenPurchase(premiumFeatures: List<PremiumFeature>, onCloseClicked: () -> Unit) {
    val activity = LocalContext.current as Activity
    val purchaseViewModel = hiltViewModel<PurchaseViewModel>()
    val products by purchaseViewModel.getAllProducts().collectAsState(initial = emptyList())
    var selectedProduct by remember(products) {
        mutableStateOf(
            products.find { it.productId == purchaseViewModel.getMostPopularProductId() }?.let {
                WrappedProduct(it)
            }
        )
    }

    Scaffold(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF171817),
                            Color(0xFF3D3D3D)
                        )
                    )
                )
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    modifier = Modifier
                        .clickable { onCloseClicked() }
                        .size(36.dp)
                        .padding(6.dp),
                    painter = painterResource(id = R.drawable.ic_gift),
                    contentDescription = null,
                    tint = Color.Unspecified
                )

                Text(
                    modifier = Modifier.weight(1f),
                    text = stringResource(R.string.upgrade_to_unlock_extra_features),
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Start,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Icon(
                    modifier = Modifier
                        .clickable { onCloseClicked() }
                        .size(36.dp)
                        .padding(6.dp),
                    imageVector = Icons.Rounded.Close,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            AdvanceFeatures(
                modifier = Modifier.fillMaxWidth(),
                features = premiumFeatures
            )

            Spacer(modifier = Modifier.height(24.dp))

            InAppProducts(
                modifier = Modifier.fillMaxWidth(),
                products = products,
                selectedProduct = selectedProduct,
                onProductSelected = {
                    selectedProduct = it
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFe64a19),
                    contentColor = Color.White,
                    disabledContainerColor = Color(0xFF818A86),
                    disabledContentColor = Color(0xFF3B403D)
                ),
                onClick = {
                    selectedProduct?.let {
                        purchaseViewModel.buy(
                            activity = activity,
                            productId = it.product.productId,
                            offerToken = it.offerToken()
                        )
                    }
                },
                contentPadding = PaddingValues(
                    horizontal = 12.dp,
                    vertical = 12.dp
                ),
                enabled = selectedProduct != null
            ) {
                Text(
                    text = "Continue".uppercase(),
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF000000),
                    contentColor = Color.White,
                    disabledContainerColor = Color(0xFF818A86),
                    disabledContentColor = Color(0xFF3B403D)
                ),
                onClick = {
                    purchaseViewModel.restorePurchase()
                },
                contentPadding = PaddingValues(
                    horizontal = 12.dp,
                    vertical = 12.dp
                )
            ) {
                Text(
                    text = "Already purchased? Restore",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = stringResource(id = R.string.text_subscription_explain),
                style = MaterialTheme.typography.labelSmall,
                textAlign = TextAlign.Start,
                color = Color.White
            )
        }
    }

}

@Composable
private fun AdvanceFeatures(modifier: Modifier, features: List<PremiumFeature>) {
    Column(
        modifier = modifier
            .background(
                color = Color(0xFF2E2E2E),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.Start,
    ) {
        features.forEach {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 16.dp,
                        vertical = 8.dp
                    ),
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Rounded.CheckCircle,
                    contentDescription = null,
                    tint = Color(0xFFCF7122)
                )

                Text(
                    text = stringResource(id = it.title),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFFFFFFFF)
                )
            }
        }
    }
}

@Composable
private fun InAppProducts(
    modifier: Modifier,
    products: List<ProductDetails>,
    selectedProduct: WrappedProduct?,
    onProductSelected: (WrappedProduct) -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        products.forEach {
            ProductItem(
                modifier = Modifier.weight(1f),
                product = WrappedProduct(it),
                isSelected = selectedProduct?.product?.productId == it.productId,
                onProductSelected = onProductSelected
            )
        }
    }
}

@Composable
private fun ProductItem(
    modifier: Modifier,
    product: WrappedProduct,
    isSelected: Boolean,
    onProductSelected: (WrappedProduct) -> Unit
) {
    val purchaseViewModel = hiltViewModel<PurchaseViewModel>()
    val isMostPopular = remember {
        product.product.productId == purchaseViewModel.getMostPopularProductId()
    }

    Box(
        modifier = modifier
            .clip(
                RoundedCornerShape(8.dp)
            )
            .clickable {
                onProductSelected(product)
            }
            .background(
                brush = Brush.verticalGradient(
                    colors = if (isSelected)
                        listOf(
                            Color(0xFFFFF4E5),
                            Color(0xFFFFDAA4)
                        )
                    else
                        listOf(Color.White, Color.White)
                ),
                shape = RoundedCornerShape(8.dp)
            )
            .border(
                width = if (isSelected) 2.dp else 0.dp,
                shape = RoundedCornerShape(8.dp),
                color = Color(0xFFe64a19)
            )
    ) {
        AnimatedVisibility(
            modifier = Modifier.align(Alignment.TopStart),
            visible = isMostPopular
        ) {
            Text(
                modifier = Modifier
                    .background(
                        color = Color(0xFFe64a19),
                        shape = RoundedCornerShape(bottomEnd = 8.dp)
                    )
                    .padding(horizontal = 8.dp, vertical = 2.dp),
                text = "Most popular",
                style = MaterialTheme.typography.labelSmall,
                color = Color.White
            )
        }


        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(
                    top = 36.dp,
                    end = 12.dp,
                    start = 12.dp,
                    bottom = 12.dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = product.title(),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black
            )

            Text(
                text = product.price(),
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFFE52F2F)
            )

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = product.description(),
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.W400
                ),
                color = Color(0xFF818A86),
                minLines = 2,
                maxLines = 2,
                textAlign = TextAlign.Center
            )
        }
    }
}