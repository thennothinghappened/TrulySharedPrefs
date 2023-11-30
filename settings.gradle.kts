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
            version("kotlin", "1.9.21")
            version("agp", "8.1.0-rc01")

            version("jetbrains-compose", "1.5.11")
            version("jetbrains-compose-compiler", "1.5.5")

            version("androidx-compose", "1.5.4")
            version("androidx-activity", "1.8.1")
            version("androidx-material3", "1.1.2")
            version("androidx-appcompat", "1.6.1")

            version("android-minSdk", "24")
            version("android-compileSdk", "34")

            plugin("kotlin-multiplatform", "org.jetbrains.kotlin.multiplatform").versionRef("kotlin")
            plugin("android-library", "com.android.library").versionRef("agp")
            plugin("android-application", "com.android.application").versionRef("agp")
            plugin("jetbrains-compose", "org.jetbrains.compose").versionRef("jetbrains-compose")

            library("androidx-compose-ui", "androidx.compose.ui", "ui").versionRef("androidx-compose")
            library("androidx-material3", "com.google.android.material3", "material3").versionRef("androidx-material3")
            library("androidx-activity-compose", "androidx.activity", "activity-compose").versionRef("androidx-activity")
            library("androidx-appcompat", "androidx.appcompat", "appcompat").versionRef("androidx-appcompat")
        }
    }
}