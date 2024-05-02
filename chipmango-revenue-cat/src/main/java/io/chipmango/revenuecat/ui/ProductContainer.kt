package io.chipmango.revenuecat.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.revenuecat.purchases.models.StoreProduct
import io.chipmango.revenuecat.ProductsResult
import io.chipmango.revenuecat.viewmodel.PaywallViewModel

@Composable
fun ProductContainer(
    paywallViewModel: PaywallViewModel = hiltViewModel(),
    productIds: List<String>,
    contentLoading: @Composable () -> Unit,
    contentError: @Composable (String) -> Unit,
    content: @Composable (List<StoreProduct>) -> Unit
) {
    val offeringResult by paywallViewModel.productResult.collectAsStateWithLifecycle(ProductsResult.Loading)

    LaunchedEffect(productIds) {
        paywallViewModel.fetchProducts(productIds)
    }

    when (offeringResult) {
        is ProductsResult.Loading -> {
            contentLoading()
        }

        is ProductsResult.Success -> {
            val products = remember { (offeringResult as ProductsResult.Success).products }
            content(products)
        }

        is ProductsResult.Error -> {
            val errorMessage = remember {
                (offeringResult as ProductsResult.Error).message
            }
            contentError(errorMessage)
        }
    }
}