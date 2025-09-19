plugins {
    alias(libs.plugins.kotlin.compose)
    id("su.tease.library")
}

library {
    withCompose()
}
