package com.chipmango.app.navigation

import androidx.compose.runtime.Composable
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.chipmango.app.ui.ScreenTestAccount
import com.chipmango.app.ui.ScreenTestHome
import io.chipmango.navigation.destination.destination
import io.chipmango.navigation.navigator.Navigator
import io.chipmango.navigation.navigator.getRequiredLong
import io.chipmango.navigation.navigator.navigate

@Composable
fun MainNavigation(navController: NavHostController) {
    Navigator(
        navController = navController,
        startingDestination = Screens.TestHome
    ) {
        destination(Screens.TestHome) {
            ScreenTestHome(
                onAccountClicked = {
                    navController.navigate(
                        destination = Screens.TestAccount,
                        arguments = bundleOf(
                            QueryParams.AccountId to 100
                        )
                    )
                }
            )
        }

        destination(Screens.TestAccount) {
            ScreenTestAccount(
                accountId = it.getRequiredLong(QueryParams.AccountId)
            )
        }
    }
}