plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.kspAndroid)
    alias(libs.plugins.kaptKotlin)
    alias(libs.plugins.kotlinParcelize)
    alias(libs.plugins.daggerHilt)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.cafely"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.cafely"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

kapt {
    correctErrorTypes = true
    arguments {
        arg("room.schemaLocation", projectDir.resolve("schemas").absolutePath)
        arg("room.incremental", "true")
    }
}

ksp {
    java {
        version = "18"
    }
}

dependencies {
    // Core and lifecycle dependencies
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))

    // Other dependencies
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidWork)
    implementation(libs.play.services.auth)

    // Firebase and Room dependencies
    implementation(libs.firebase.firestore)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.roomktx)
    implementation(libs.roomruntime)
    ksp(libs.kspRoomCompiler)
    annotationProcessor(libs.kspRoomCompiler)

    // Hilt dependencies
    implementation(libs.hilt)
    kapt(libs.hiltCompiler)
    implementation(libs.hiltNavigation)
    implementation(libs.hiltWork)

    // Compose dependencies
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.coil.compose)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.credentials)
    implementation (libs.androidx.navigation.compose)
    implementation (libs.lottie.compose)
    implementation (libs.androidx.animation)
    implementation (libs.google.accompanist.flowlayout)
    implementation(libs.colorpicker.compose)
    implementation (libs.androidx.work.runtime.ktx.v271)
    implementation (libs.gson)

    // Testing dependencies
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    // Debugging tools
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}