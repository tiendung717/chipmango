package com.chipmango.app.navigation

import androidx.navigation.NavType
import io.chipmango.navigation.destination.DestinationArgument
import io.chipmango.navigation.destination.DestinationFactory

object QueryParams {
    const val AccountId = "accountId"
}

object Screens {
    private val factory by lazy {
        DestinationFactory("solid", "app")
    }

    val Home = factory.create(
        path = "home",
        screenName = "Screen Home",
        screenClass = "ScreenHome"
    )

    val NewRecord = factory.create(
        path = "new-record",
        screenName = "Screen New Record",
        screenClass = "ScreenNewRecord"
    )

    val Settings = factory.create(
        path = "settings",
        screenName = "Screen Home",
        screenClass = "ScreenHome"
    )

    // Test screens
    val TestHome = factory.create(
        path = "test_home",
        screenName = "Screen Test",
        screenClass = "ScreenTest"
    )

    val TestAccountList = factory.create(
        path = "test_account_list",
        screenName = "Screen Test",
        screenClass = "ScreenTest"
    )

    val TestAccount = factory.create(
        path = "test_account",
        screenName = "Screen Test",
        screenClass = "ScreenTest",
        arguments = arrayOf(
            QueryParams.AccountId to NavType.LongType
        )
    )

    val TestAccountTags = factory.create(
        path = "test_account_tags",
        screenName = "Screen Test",
        screenClass = "ScreenTest",
        arguments = arrayOf(
            QueryParams.AccountId to NavType.LongType
        )
    )
}