pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

rootProject.name = "RAWG"
include(":app")

include(":features:home")
include(":features:detail")
include(":features:favorite")

include(":libraries:common")
include(":libraries:network")
include(":libraries:navigation")
