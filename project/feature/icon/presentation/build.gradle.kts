plugins {
    id("su.tease.presentation")
}

presentation {
    dependencies {
        implementation(libs.coil.asProvider())
        implementation(libs.coil.svg)
    }
}