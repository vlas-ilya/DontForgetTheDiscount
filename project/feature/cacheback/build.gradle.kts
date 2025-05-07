plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
}

android {
    namespace = "su.tease.project.feature.cacheback"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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

    kotlinOptions {
        jvmTarget = "11"
    }

    room {
        schemaDirectory("$rootDir/schemas")
    }
}

dependencies {
    // Architecture
    implementation(libs.decompose)
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)

    // Network
    implementation(libs.retrofit)

    // core
    implementation(libs.kotlin.collections.immutable)
    implementation(libs.kotlin.coroutines.core)
    implementation(libs.timber)
    testImplementation(libs.junit)
    testImplementation(libs.kotlin.coroutines.test)

    // Database
    ksp(libs.room.compiler)
    annotationProcessor(libs.room.compiler)
    implementation(libs.room.ktx)
    debugImplementation(libs.room.testing)

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

    // project
    implementation(project(":project:design:icons"))
    implementation(project(":project:design:component:navigation_bar"))
    implementation(project(":project:design:component:controls"))
    implementation(project(":project:design:theme:impl"))
    implementation(project(":project:design:theme:api"))
    implementation(project(":project:core:clean"))
    implementation(project(":project:core:mvi:api"))
    implementation(project(":project:core:mvi:impl"))
    implementation(project(":project:core:mvi:integration:clean"))
    implementation(project(":project:core:mvi:integration:component"))
    implementation(project(":project:core:mvi:integration:navigation"))
    implementation(project(":project:core:mvi:middleware:intercept"))
    implementation(project(":project:core:mvi:middleware:suspend"))
    implementation(project(":project:core:navigation"))
    implementation(project(":project:core:utils"))
    implementation(project(":project:feature:notification:api"))
}
