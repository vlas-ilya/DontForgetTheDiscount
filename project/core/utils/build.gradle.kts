plugins {
    alias(libs.plugins.kotlin.compose)
    id("su.tease.library")
}

library {
    withCompose()
    withKoin()
    withNetwork()

    dependencies {
        implementation(project(":project:core:mvi:api"))
    }
}
