plugins {
    alias(libs.plugins.kotlin.compose)
    id("su.tease.library")
}

library {
    withCompose()

    dependencies {
        implementation(project(":project:design:theme:api"))
    }
}
