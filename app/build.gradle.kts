import Dependencies.implementCompose
import Dependencies.implementHilt
import Dependencies.implementLifecycle
import Dependencies.implementNavigation
import Dependencies.implementCore

plugins {
    id(Dependencies.Plugin.androidApplication)
    id(Dependencies.Plugin.kotlinAndroid)
    id(Dependencies.Plugin.kapt)
    id(Dependencies.Plugin.hilt)
}

android {
    namespace = AppConfig.applicationID
    compileSdk = AppConfig.compileSdk

    defaultConfig {
        applicationId = AppConfig.applicationID
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.compileSdk

        versionCode = AppConfig.versionCode
        versionName = AppConfig.versionName

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

    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementCore()
    implementLifecycle()
    implementNavigation()
    implementCompose()
    implementHilt()

    addModule(Feature.home)
    addModule(Feature.detail)
    addModule(Feature.favorite)

    addModule(Libraries.common)
    addModule(Libraries.navigation)
}