plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.ksp)
}

android {
    namespace = "com.hungho.data"

    compileSdk = 35

    defaultConfig {
        minSdk = 24
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    flavorDimensions += "environment"
    productFlavors {
        create("dev") {
            buildConfigField("String", "BASE_URL", "\"https://api.github.com\"")
        }

        create("prod") {
            buildConfigField("String", "BASE_URL", "\"https://api.github.com\"")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    ksp {
        arg("room.schemaLocation", "$projectDir/schemas")
    }

    buildFeatures {
        buildConfig = true
    }

    externalNativeBuild {
        cmake {
            path = file("src/main/cpp/CMakeLists.txt")
            version = "3.18.1"
        }
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(libs.androidx.core.ktx)

    //OkHttp
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)

    //Gson
    api(libs.gson)

    //Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)

    //Database
    implementation(libs.room)
    implementation(libs.room.ktx)
    implementation(libs.room.paging)
    implementation(libs.sqlite)
    implementation(libs.sqlCypher)
    ksp(libs.room.compiler)

    //DI
    implementation(libs.koin)
    implementation(libs.koin.android)

    //Crypto
    implementation(libs.securityCrypto)



    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.coroutines.test)
    testImplementation(libs.paging.test)
    testImplementation(libs.paging.common)
    testImplementation(libs.androidx.test)
    testImplementation(libs.androidx.junit)
    testImplementation(libs.robolectric)
    testImplementation(libs.room.testing)
}
