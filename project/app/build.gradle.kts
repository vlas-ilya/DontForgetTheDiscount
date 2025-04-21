plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
}

android {
    namespace = "su.tease.dontforgetthediscount"
    compileSdk = 35

    defaultConfig {
        applicationId = "su.tease.dontforgetthediscount"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

    room {
        schemaDirectory("$projectDir/schemas")
    }
}

dependencies {
    // Android
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlin.coroutines.android)
    androidTestImplementation(libs.androidx.junit)

    // Architecture
    implementation(libs.decompose)
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)
    implementation(libs.koin.android)

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
    implementation(libs.timber)
    testImplementation(libs.junit)
    testImplementation(libs.kotlin.coroutines.test)

    // Database
    ksp(libs.room.compiler)
    annotationProcessor(libs.room.compiler)
    implementation(libs.room.ktx)
    debugImplementation(libs.room.testing)

    // Network
    implementation(libs.retrofit)
    implementation(libs.retrofit.serialization)
    implementation(libs.kotlin.serialization)
    implementation(libs.okhttp3.logging)

    // project
    implementation(project(":project:feature:main"))
    implementation(project(":project:feature:splash"))
    implementation(project(":project:feature:cacheback"))
    implementation(project(":project:design:icons"))
    implementation(project(":project:design:component:navigation_bar"))
    implementation(project(":project:design:theme:impl"))
    implementation(project(":project:design:theme:api"))
    implementation(project(":project:core:mvi:api"))
    implementation(project(":project:core:mvi:impl"))
    implementation(project(":project:core:mvi:integration:android"))
    implementation(project(":project:core:mvi:integration:clean"))
    implementation(project(":project:core:mvi:integration:component"))
    implementation(project(":project:core:mvi:integration:navigation"))
    implementation(project(":project:core:mvi:middleware:intercept"))
    implementation(project(":project:core:mvi:middleware:suspend"))
    implementation(project(":project:core:navigation"))
    implementation(project(":project:core:utils"))
}
