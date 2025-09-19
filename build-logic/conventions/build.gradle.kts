plugins {
    `kotlin-dsl`
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.parcelize) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.kotlin.compose) apply false
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}

gradlePlugin {
    plugins {
        register("domain") {
            id = "su.tease.domain"
            implementationClass = "su.tease.project.buildlogic.DomainConventionPlugin"
        }
        register("data") {
            id = "su.tease.data"
            implementationClass = "su.tease.project.buildlogic.DataConventionPlugin"
        }
        register("presentation") {
            id = "su.tease.presentation"
            implementationClass = "su.tease.project.buildlogic.PresentationConventionPlugin"
        }
        register("integration") {
            id = "su.tease.integration"
            implementationClass = "su.tease.project.buildlogic.IntegrationConventionPlugin"
        }
        register("library") {
            id = "su.tease.library"
            implementationClass = "su.tease.project.buildlogic.LibraryConventionPlugin"
        }
    }
}

private fun PluginDependency.toDependencyNotation() = "${pluginId}:${pluginId}.gradle.plugin:${version}"

dependencies {
    compileOnly(libs.plugins.android.library.get().toDependencyNotation())
    compileOnly(libs.plugins.kotlin.android.get().toDependencyNotation())
    compileOnly(libs.plugins.kotlin.parcelize.get().toDependencyNotation())
    compileOnly(libs.plugins.kotlin.serialization.get().toDependencyNotation())
    compileOnly(libs.plugins.kotlin.compose.get().toDependencyNotation())
}
