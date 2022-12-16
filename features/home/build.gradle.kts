
import Dependencies.implementCompose
import Dependencies.implementCore
import Dependencies.implementHilt
import Dependencies.implementLifecycle
import Dependencies.implementNavigation
import Dependencies.implementNetworkCore
import Dependencies.implementTest

plugins {
    id(Dependencies.Plugin.androidLibrary)
    id(Dependencies.Plugin.kotlinAndroid)
    id(Dependencies.Plugin.kapt)
    id(Dependencies.Plugin.hilt)
}

android {
    namespace = "${AppConfig.basePackage}.home"
    compileSdk = AppConfig.compileSdk

    defaultConfig {
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.compileSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = AppConfig.jvmTarget
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = AppConfig.kotlinCompilerExtVersion
    }
}

dependencies {
    implementCore()
    implementCompose()
    implementHilt()
    implementLifecycle()
    implementNavigation()
    implementNetworkCore()

    implementation(Dependencies.Android.constraintLayout)
    implementation(Dependencies.Android.pagging)
    implementation(Dependencies.ThirdParty.glideCompose)

    addModule(Libraries.common)
    addModule(Libraries.navigation)
    addModule(Libraries.network)

    implementTest()
}