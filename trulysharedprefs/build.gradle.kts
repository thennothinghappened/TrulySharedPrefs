
group = "org.orca.trulysharedprefs"
version = "1.0.0"

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    `maven-publish`
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }

    jvm("desktop")

    sourceSets {
        commonMain.dependencies {

        }

        androidMain.dependencies {

        }

        val desktopMain by getting {
            dependencies {

            }
        }
    }
}

android {
    namespace = "org.orca.trulysharedprefs"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.compileSdk.get().toInt()
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