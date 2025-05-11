plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kover)
}

android {
    namespace = "com.hungho.githubusers"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.hungho.githubusers"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    flavorDimensions += "environment"
    productFlavors {
        create("dev") {
            applicationIdSuffix = ".dev"
        }

        create("prod") {}
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        buildConfig = true
        viewBinding = true
    }

    kover {
        reports {
            filters {
                excludes {
                    classes(
                        "*.*BuildConfig*",
                        "*.*Module*",
                        "*.*Factory*",
                        "*.*Fragment*",
                        "*.*Activity*",
                        "*.*Database*",
                        "*.*Database_Impl*",
                        "*.retrofit.*Services*",
                        "*.App",
                        "*.BuildFlavor",
                        "*.SecretHelper",
                        "*.AndroidKeyStoreProvider",
                    )
                    packages(
                        "com.hungho.*.di.*",
                        "com.hungho.githubusers.databinding",
                        "com.hungho.githubusers.ui.utils",
                        "com.hungho.githubusers.ui.base",
                        "com.hungho.githubusers.ui.dialog",
                        "com.hungho.githubusers.ui.view",
                        "com.hungho.githubusers.ui.feature.*.adapter",
                    )
                }
            }
        }
    }
}

dependencies {
    kover(project(":domain"))
    kover(project(":data"))

    implementation(project(":data"))
    implementation(project(":domain"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    //Logger
    implementation(libs.timber)

    //DI
    implementation(libs.koin)
    implementation(libs.koin.android)
    implementation(libs.koin.android.compat)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation)

    //Image
    implementation(libs.glide)

    //SwipeToRefresh
    implementation(libs.swipeRefreshLayout)

    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.coroutines.test)
    testImplementation(libs.androidx.core.testing)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}