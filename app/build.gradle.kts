plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
}

android {
    namespace = "com.torre.proyectofinal"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.torre.proyectofinal"
        minSdk = 26
        targetSdk = 35
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
            excludes += "/META-INF/INDEX.LIST"
            excludes += "/META-INF/DEPENDENCIES"  // Agregado para excluir el archivo DEPENDENCIES
        }
    }
}

dependencies {
    // Dependencias principales de Jetpack y AndroidX
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // Dependencias de Room (Base de datos)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.androidx.storage)
    implementation(libs.androidx.media3.common.ktx)
    implementation(libs.firebase.appdistribution.gradle)
    //implementation(libs.androidx.ui.test.android) // Room Runtime
    kapt(libs.androidx.room.compiler) // Room Compiler
    implementation("androidx.room:room-ktx:2.5.2")


    // Dependencias de Coroutines (para operaciones asincrónicas)
    implementation(libs.kotlinx.coroutines.core) // Coroutines Core
    implementation(libs.kotlinx.coroutines.android) // Coroutines Android

    // Dependencias de Lifecycle (para gestionar ciclo de vida y datos en la UI)
    implementation(libs.androidx.lifecycle.viewmodel.ktx) // ViewModel KTX
    implementation(libs.androidx.lifecycle.livedata.ktx) // LiveData KTX

    // Dependencias para pruebas unitarias y de UI
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Dependencias adicionales para pruebas de Compose
    androidTestImplementation(platform(libs.androidx.compose.bom))

    //Dependencias habilitadas por imposibilidad de versión
    //androidTestImplementation(libs.androidx.ui.test.junit4)
    //debugImplementation(libs.androidx.ui.tooling)
    //debugImplementation(libs.androidx.ui.test.manifest)

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0") // Convierte JSON en objetos de Kotlin

    // OkHttp (para ver logs de las peticiones, útil para depuración)
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.3")

}

kapt {
    correctErrorTypes = true
}
