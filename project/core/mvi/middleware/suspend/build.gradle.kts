plugins {
    alias(libs.plugins.kotlin.compose)
    id("su.tease.library")
}

library {
    withCompose()

    dependencies {
        implementation(project(":project:core:mvi:api"))
        implementation(project(":project:core:mvi:impl"))
        implementation(project(":project:core:utils"))
    }
}
