import Dependencies.implementNavigation

plugins {
    id(Dependencies.Plugin.androidLibrary)
    id(Dependencies.Plugin.kotlinAndroid)
    id(Dependencies.Plugin.kapt)
}

android {
    namespace = "${AppConfig.basePackage}.navigation"
    compileSdk = AppConfig.compileSdk

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = AppConfig.kotlinCompilerExtVersion
    }
}

dependencies {
    implementNavigation()

    implementation(Dependencies.ThirdParty.hiltAndroid)
}