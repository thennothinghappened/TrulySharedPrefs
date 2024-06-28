import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl

group = "org.orca.trulysharedprefs"
version = "1.1.0"

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    id("maven-publish")
}

kotlin {

    compilerOptions {
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }

    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }

        publishLibraryVariants("release", "debug")
    }

    jvm("desktop")

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs()

    sourceSets {
        val desktopMain by getting
        val wasmJsMain by getting
    }

}

android {

    namespace = "org.orca.trulysharedprefs"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
        compileSdk = libs.versions.android.compileSdk.get().toInt()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

/**
 * GitHub User ID for publishing to GitHub Packages.
 */
val githubUser: String? = project.findProperty("gpr.user") as String?
    ?: System.getenv("GITHUB_USER")

/**
 * GitHub Token for publishing to GitHub Packages.
 */
val githubToken: String? = project.findProperty("gpr.key") as String?
    ?: System.getenv("GITHUB_TOKEN")

publishing {
    repositories {
        if (githubUser != null && githubToken != null) {
            maven {
                setUrl("https://maven.pkg.github.com/thennothinghappened/TrulySharedPrefs")
                credentials {
                    username = githubUser
                    password = githubToken
                }
            }
        }
    }
}
