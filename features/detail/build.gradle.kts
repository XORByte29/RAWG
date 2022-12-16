
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
    namespace = "${AppConfig.basePackage}.detail"
    compileSdk = AppConfig.compileSdk

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
    implementation(Dependencies.ThirdParty.glideCompose)

    addModule(Libraries.common)
    addModule(Libraries.navigation)
    addModule(Libraries.network)

    implementTest()
}