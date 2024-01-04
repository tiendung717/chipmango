import deps.dependOn

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("kotlin-android")
    id("kotlin-parcelize")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.solid.test"
    compileSdk = Build.compileSdk

    defaultConfig {
        applicationId = "com.solid.test"
        minSdk = Build.minSdk
        targetSdk = Build.targetSdk
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget =  JavaVersion.VERSION_17.toString()
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = deps.Compose.Versions.composeCompiler
    }
}

dependencies {
    implementation(project(":chipmango"))
    implementation(project(":chipmango-ad"))
    implementation(project(":chipmango-permission"))
    implementation(project(":chipmango-uikit"))
    implementation(project(":chipmango-rating"))

    dependOn(
        deps.AndroidX,
        deps.Compose,
        deps.Hilt,
        deps.Log,
        deps.Ads
    )
}
