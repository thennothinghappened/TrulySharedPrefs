
rootProject.name = "TrulySharedPrefs"

include(":trulysharedprefs")
include(":sample")

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    @Suppress("UnstableApiUsage")
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }

    versionCatalogs {
        create("libs") {
            version("jvmTarget", "17")
            version("kotlin", "2.0.0")
            version("agp", "8.2.0")

            version("jetbrains-compose-compiler", "1.6.11")

            version("androidx-compose", "1.6.7")
            version("androidx-activityCompose", "1.9.0")
            version("androidx-appcompat", "1.7.0")

            version("android-minSdk", "24")
            version("android-compileSdk", "34")

            plugin("kotlin-multiplatform", "org.jetbrains.kotlin.multiplatform").versionRef("kotlin")
            plugin("android-library", "com.android.library").versionRef("agp")
            plugin("android-application", "com.android.application").versionRef("agp")
            plugin("compose-compiler", "org.jetbrains.compose").versionRef("jetbrains-compose-compiler")
            plugin("compose", "org.jetbrains.kotlin.plugin.compose").versionRef("kotlin")

            library("androidx-compose-ui", "androidx.compose.ui", "ui").versionRef("androidx-compose")
            library("androidx-activity-compose", "androidx.activity", "activity-compose").versionRef("androidx-activityCompose")
            library("androidx-appcompat", "androidx.appcompat", "appcompat").versionRef("androidx-appcompat")
        }
    }
}
