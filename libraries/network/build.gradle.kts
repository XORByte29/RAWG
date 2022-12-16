import Dependencies.implementCompose
import Dependencies.implementHilt
import Dependencies.implementNetworkCore
import Dependencies.implementCore

plugins {
    id(Dependencies.Plugin.androidLibrary)
    id(Dependencies.Plugin.kotlinAndroid)
    id(Dependencies.Plugin.kapt)
    id(Dependencies.Plugin.hilt)
}

android {
    namespace = "${AppConfig.basePackage}.network"
    compileSdk = AppConfig.compileSdk
}

dependencies {
    implementCore()
    implementHilt()
    implementCompose()
    implementNetworkCore()
}