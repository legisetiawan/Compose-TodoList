plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)

    id("com.google.devtools.ksp")
}

android {
    namespace = "com.legisetiawan.todolistapp"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "com.legisetiawan.todolistapp"
        minSdk = 23
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            optimization {
                enable = true
                packageScope = setOf("androidx.**", "kotlin.**", "kotlinx.**")
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    // ========== COMPOSE BOM (WAJIB!) ==========
    // Ini akan menyelaraskan SEMUA versi Compose secara otomatis
//    val composeBom = platform("androidx.compose:compose-bom:2024.02.00")
//    implementation(composeBom)
//    androidTestImplementation(composeBom)
//
//    // Material 3
//    implementation("androidx.compose.material3:material3")
//    implementation("androidx.compose.ui:ui")
//    implementation("androidx.compose.ui:ui-graphics")
//    implementation("androidx.compose.ui:ui-tooling-preview")
//    implementation("androidx.compose.material:material-icons-extended")
//    debugImplementation("androidx.compose.ui:ui-tooling")
//
//    // Activity Compose
//    implementation("androidx.activity:activity-compose:1.8.2")
//
//    // ========== ROOM DATABASE ==========
//    implementation("androidx.room:room-runtime:2.6.1")
//    ksp("androidx.room:room-compiler:2.6.1")
//    implementation("androidx.room:room-ktx:2.6.1")

    // ========== VIEWMODEL & LIFECYCLE ==========
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.7.0")

    // ========== NAVIGATION ==========
    implementation("androidx.navigation:navigation-compose:2.7.7")

    // ========== COROUTINES ==========
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)

    // 1. Room Database
//    implementation("androidx.room:room-runtime:2.6.1")
//    ksp("androidx.room:room-compiler:2.6.1")
//    implementation("androidx.room:room-ktx:2.6.1")

    // 2. ViewModel & Lifecycle Compose
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.7.0")

    // 3. Navigation Compose
    implementation("androidx.navigation:navigation-compose:2.7.7")

    // 4. Coroutines (biasanya sudah ada, tapi pastikan)
//    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
}