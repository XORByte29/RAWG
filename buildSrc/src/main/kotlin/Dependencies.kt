import org.gradle.api.artifacts.dsl.DependencyHandler

object Dependencies {

    object Plugin {
        const val androidVersion = "7.3.1"
        const val composeVersion = "1.2.1"
        const val kotlinAndroidVersion = "1.7.10"

        const val kotlinAndroid = "org.jetbrains.kotlin.android"
        const val androidApplication = "com.android.application"
        const val androidLibrary = "com.android.library"
        const val compose = "org.jetbrains.compose"
        const val kapt = "org.jetbrains.kotlin.kapt"
        const val hilt = "com.google.dagger.hilt.android"
    }

    object ClassPath {
        private const val gradleVersion = "7.0.4"
        private const val kotlinVersion = "1.6.10"
        private const val hiltVersion = "2.44"

        const val gradle = "com.android.tools.build:gradle:$gradleVersion"
        const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"
        const val hiltGradlePlugin = "com.google.dagger:hilt-android-gradle-plugin:$hiltVersion"
    }

    object Android {
        private const val coreKtxVersion = "1.9.0"
        private const val appCompatVersion = "1.5.1"
        private const val lifecycleVersion = "2.5.1"
        private const val lifecycleExtVersion = "2.2.0"
        private const val activityVersion = "1.6.1"
        private const val composeVersion = "1.3.1"
        private const val material3Version = "1.0.1"
        private const val navigationVersion = "2.5.3"
        private const val hiltNavigationVersion = "1.0.0"
        private const val constraintLayoutVersion = "1.0.1"
        private const val hiltVersion = "1.0.0-alpha03"
        private const val pagingVersion = "1.0.0-alpha17"
        private const val roomVersion = "2.4.2"

        const val coreKtx = "androidx.core:core-ktx:$coreKtxVersion"
        const val appCompat = "androidx.appcompat:appcompat:$appCompatVersion"
        const val activityCompose = "androidx.activity:activity-compose:$activityVersion"

        // lifecycle
        const val lifecycleExtensions = "androidx.lifecycle:lifecycle-extensions:$lifecycleExtVersion"
        const val lifecycleRuntimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion"
        const val lifecycleViewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion"
        const val lifecycleViewModelCompose = "androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycleVersion"

        // compose
        const val composeUi = "androidx.compose.ui:ui:$composeVersion"
        const val composeUiTooling = "androidx.compose.ui:ui-tooling:$composeVersion"
        const val composeFoundation = "androidx.compose.foundation:foundation:$composeVersion"
        const val composeUiToolingPreview = "androidx.compose.ui:ui-tooling-preview:$composeVersion"
        const val composeUitTestManifest = "androidx.compose.ui:ui-test-manifest:$composeVersion"
        const val composeMaterial = "androidx.compose.material3:material3:$material3Version"
        const val composeMaterialWindow = "androidx.compose.material3:material3-window-size-class:$material3Version"
        const val composeMaterialIcon = "androidx.compose.material:material-icons-extended:$composeVersion"

        // Navigation
        const val navigationCompose = "androidx.navigation:navigation-compose:$navigationVersion"

        // Paging
        const val pagging = "androidx.paging:paging-compose:$pagingVersion"

        // Constrain Layout
        const val constraintLayout = "androidx.constraintlayout:constraintlayout-compose:$constraintLayoutVersion"

        // Hilt
        const val hiltNavigation = "androidx.hilt:hilt-navigation-compose:$hiltNavigationVersion"
        const val hiltCompiler = "androidx.hilt:hilt-compiler:$hiltVersion"

        const val roomRuntime = "androidx.room:room-runtime:$roomVersion"
        const val roomCompiler = "androidx.room:room-compiler:$roomVersion"
        const val roomCore = "androidx.room:room-ktx:$roomVersion"
    }

    object ThirdParty {
        private const val coroutinesVersion = "1.6.0"
        private const val retrofitVersion = "2.9.0"
        private const val hiltVersion = "2.44"
        private const val okHttpVersion = "5.0.0-alpha.10"
        private const val glideVersion = "1.0.0-alpha.1"

        // Coroutines
        const val kotlinxCoroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion"
        const val kotlinxCoroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion"

        // Retrofit
        const val retrofit = "com.squareup.retrofit2:retrofit:$retrofitVersion"
        const val retrofitConverterGson = "com.squareup.retrofit2:converter-gson:$retrofitVersion"

        // OkHttp
        const val okHttp = "com.squareup.okhttp3:okhttp:$okHttpVersion"
        const val logginInterceptor = "com.squareup.okhttp3:logging-interceptor:$okHttpVersion"

        // Gilde
        const val glideCompose = "com.github.bumptech.glide:compose:$glideVersion"

        // Hilt
        const val hiltAndroid = "com.google.dagger:hilt-android:$hiltVersion"
        const val hiltAndroidCompiler = "com.google.dagger:hilt-android-compiler:$hiltVersion"
        const val hiltCompiler = "com.google.dagger:hilt-compiler:$hiltVersion"
    }

    object Test {
        private const val jnitVersion = "4.13.2"
        private const val junitExtVersion = "1.1.4"
        private const val archCoretestingVersion = "2.1.0"
        private const val mockkVersion = "1.11.0"
        private const val kotlinVersion = "1.6.4"

        const val junit = "junit:junit:$jnitVersion"
        const val junitExt = "androidx.test.ext:junit-ktx:$junitExtVersion"
        const val archCoreTesting = "androidx.arch.core:core-testing:$archCoretestingVersion"
        const val mockk = "io.mockk:mockk:$mockkVersion"
        const val kotlinXCoroutineTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$kotlinVersion"
    }

    fun DependencyHandler.implementCore() {
        implementation(Android.coreKtx)
        implementation(Android.appCompat)
        implementation(Android.activityCompose)
        implementation(ThirdParty.kotlinxCoroutinesCore)
        implementation(ThirdParty.kotlinxCoroutinesAndroid)
    }

    fun DependencyHandler.implementLifecycle() {
        implementation(Android.lifecycleExtensions)
        implementation(Android.lifecycleRuntimeKtx)
        implementation(Android.lifecycleViewModelKtx)
        implementation(Android.lifecycleViewModelCompose)
    }

    fun DependencyHandler.implementCompose() {
        implementation(Android.composeUi)
        implementation(Android.composeFoundation)
        implementation(Android.composeUiToolingPreview)
        implementation(Android.composeMaterial)
        implementation(Android.composeMaterialWindow)
        implementation(Android.composeMaterialIcon)

        debugImplementation(Android.composeUiTooling)
        debugImplementation(Android.composeUitTestManifest)
    }

    fun DependencyHandler.implementNavigation() {
        implementation(Android.navigationCompose)
    }

    fun DependencyHandler.implementNetworkCore() {
        implementation(ThirdParty.retrofit)
        implementation(ThirdParty.retrofitConverterGson)

        implementation(ThirdParty.okHttp)
        implementation(ThirdParty.logginInterceptor)
    }

    fun DependencyHandler.implementHilt() {
        implementation(Android.hiltNavigation)
        implementation(ThirdParty.hiltAndroid)

        kapt(Android.hiltCompiler)
        kapt(ThirdParty.hiltAndroidCompiler)
        kapt(ThirdParty.hiltCompiler)
    }

    fun DependencyHandler.implementRoom() {
        implementation(Android.roomCore)
        implementation(Android.roomRuntime)

        kapt(Android.roomCompiler)
    }

    fun DependencyHandler.implementTest() {
        testImplementation(Test.junit)
        testImplementation(Test.junitExt)
        testImplementation(Test.archCoreTesting)
        testImplementation(Test.mockk)
        testImplementation(Test.kotlinXCoroutineTest)
    }
}