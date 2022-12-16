plugins {
    id(Dependencies.Plugin.androidApplication).version(Dependencies.Plugin.androidVersion).apply(false)
    id(Dependencies.Plugin.androidLibrary).version(Dependencies.Plugin.androidVersion).apply(false)
    id(Dependencies.Plugin.compose).version(Dependencies.Plugin.composeVersion).apply(false)
    id(Dependencies.Plugin.kotlinAndroid).version(Dependencies.Plugin.kotlinAndroidVersion).apply(false)
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(Dependencies.ClassPath.gradle)
        classpath(Dependencies.ClassPath.kotlinGradlePlugin)
        classpath(Dependencies.ClassPath.hiltGradlePlugin)
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
