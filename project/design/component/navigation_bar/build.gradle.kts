plugins {
    alias(libs.plugins.kotlin.compose)
    id("su.tease.library")
}

library {
    withCompose()

    dependencies {
        implementation(project(":project:design:icons"))
        implementation(project(":project:design:theme:impl"))
        implementation(project(":project:design:theme:api"))
        implementation(project(":project:core:utils"))
    }
}
