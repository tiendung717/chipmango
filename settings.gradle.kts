pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven("https://jitpack.io")
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Android Wizard"
include(":app")
include(":chipmango")
include(":chipmango-ad")
include(":chipmango-iap")
include(":chipmango-room-converters")
include(":chipmango-bluetooth")
include(":chipmango-permission")
include(":chipmango-uikit")
include(":chipmango-revenue-cat")
include(":chipmango-rating")
