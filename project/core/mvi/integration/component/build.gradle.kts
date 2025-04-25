plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.parcelize)
}

android {
    namespace = "su.tease.core.miv_component"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildFeatures {
        compose = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.15"
    }

    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    // compose
    val composeBom = platform(libs.compose.bom)
    implementation(composeBom)
    implementation(libs.compose.material)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.material.adaptive)
    debugImplementation(libs.compose.ui.tooling)
    debugImplementation(libs.compose.ui.test.manifest)
    androidTestImplementation(composeBom)
    androidTestImplementation(libs.compose.ui.test)

    // core
    implementation(libs.kotlin.coroutines.core)
    testImplementation(libs.junit)
    testImplementation(libs.kotlin.coroutines.test)

    // Architecture
    implementation(libs.decompose)
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)

    // project
    implementation(project(":project:core:mvi:api"))
    implementation(project(":project:core:mvi:impl"))
    implementation(project(":project:core:mvi:integration:navigation"))
    implementation(project(":project:core:navigation"))
    implementation(project(":project:core:utils"))
}
