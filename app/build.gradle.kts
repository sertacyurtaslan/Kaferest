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
    namespace = "com.example.kaferest"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.kaferest"
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
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.8"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "META-INF/DEPENDENCIES"
            excludes += "META-INF/LICENSE"
            excludes += "META-INF/LICENSE.txt"
            excludes += "META-INF/license.txt"
            excludes += "META-INF/NOTICE"
            excludes += "META-INF/NOTICE.txt"
            excludes += "META-INF/notice.txt"
            excludes += "META-INF/*.kotlin_module"
            excludes += "META-INF/LICENSE.md"
            excludes += "META-INF/NOTICE.md"
            excludes += "META-INF/*.properties"
            excludes += "META-INF/mailcap"
            excludes += "META-INF/mimetypes.default"
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
    arg("jvm.target", "17")
}

dependencies {
    // Core and lifecycle dependencies
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.play.services.auth.v2070)

    // Other dependencies
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidWork)
    implementation(libs.play.services.auth)
    implementation(libs.sendgrid.java)

    // Firebase and Room dependencies
    implementation(libs.firebase.firestore)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.roomktx)
    implementation(libs.roomruntime)
    implementation(libs.firebase.appcheck.debug)
    ksp(libs.kspRoomCompiler)
    annotationProcessor(libs.kspRoomCompiler)
    implementation(libs.firebase.storage.ktx)

    // Firebase App Check
    implementation(libs.firebase.appcheck.playintegrity)
    implementation(libs.firebase.appcheck.ktx)

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

    // Add Retrofit dependencies
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)

    // AWS SDK dependencies
    implementation(libs.aws.java.sdk.ses) {
        exclude(group = "com.amazonaws", module = "aws-java-sdk-core")
        exclude(group = "org.apache.httpcomponents", module = "httpclient")
    }
    implementation("com.amazonaws:aws-java-sdk-core:1.12.621") {
        exclude(group = "org.apache.httpcomponents", module = "httpclient")
        exclude(group = "org.apache.httpcomponents", module = "httpcore")
    }
    implementation(libs.okhttp)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.coroutines.core)

    // Remove previous AWS dependencies
    implementation(libs.android.mail)
    implementation(libs.android.activation)

    // Google Sign In
    implementation(libs.play.services.auth)
    implementation(libs.androidx.datastore.preferences)

    // QR Code Generation
    implementation(libs.core)
    implementation(libs.zxing.android.embedded)

    // Google Maps
    implementation(libs.maps.compose)
    implementation(libs.play.services.maps)
    implementation(libs.play.services.location)

    // Activity Result API
    implementation(libs.androidx.activity.compose.v172)

    implementation(libs.reorderable)

    // Reorderable composable
    implementation("org.burnoutcrew.composereorderable:reorderable:0.9.6")
}