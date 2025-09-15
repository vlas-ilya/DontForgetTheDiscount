plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
}

android {
    namespace = "su.tease.project.feature.preset.data"
    compileSdk = 35

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

    // project
    implementation(project(":project:core:clean"))
    implementation(project(":project:core:utils"))
    implementation(project(":project:feature:preset:domain"))
}
