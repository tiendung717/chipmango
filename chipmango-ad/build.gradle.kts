import deps.dependOn
import org.jetbrains.kotlin.konan.properties.Properties
import java.io.FileInputStream

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("kotlin-android")
    id("kotlin-parcelize")
    id("dagger.hilt.android.plugin")
}

val versions = rootProject.file("version.properties")
val props = Properties()
props.load(FileInputStream(versions))
val major = props["majorVersion"].toString().toInt()
val minor = props["minorVersion"].toString().toInt()
val patch = props["patchVersion"].toString().toInt()

android {
    namespace = "io.chipmango.ad"
    compileSdk = 33

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        aarMetadata {
            minCompileSdk = 29
        }
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
        jvmTarget = JavaVersion.VERSION_17.toString()
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = deps.Compose.Versions.composeCompiler
    }
}

ext {
    set("PUBLISH_GROUP_ID", "io.github.tiendung717")
    set("PUBLISH_ARTIFACT_ID", "chipmango-ad")
    set("PUBLISH_VERSION", "$major.$minor.$patch")
}

apply {
    from("${rootDir}/scripts/publish-module.gradle")
}

dependencies {
    dependOn(
        deps.AndroidX,
        deps.Compose,
        deps.Hilt,
        deps.Log,
        deps.Ads
    )
}