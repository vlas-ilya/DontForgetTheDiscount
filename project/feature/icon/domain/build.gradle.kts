plugins {
    id("su.tease.domain")
}

domain {
    dependencies {
        implementation(libs.androidx.ui.geometry)
        implementation(libs.androidx.core.ktx)
    }
}
