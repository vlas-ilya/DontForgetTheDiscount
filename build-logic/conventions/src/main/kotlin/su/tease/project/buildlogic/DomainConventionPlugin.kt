package su.tease.project.buildlogic

import com.android.build.gradle.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.project
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.io.File

class DomainConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

        pluginManager.apply("com.android.library")
        pluginManager.apply("org.jetbrains.kotlin.android")
        pluginManager.apply("org.jetbrains.kotlin.plugin.parcelize")
        pluginManager.apply("org.jetbrains.kotlin.plugin.serialization")

        val extension = extensions.create("domain", DomainExtension::class.java, this)
        val id = project.projectDir.toRelativeString(project.rootDir)
            .split(File.separator)
            .joinToString(".", "su.tease.")

        extensions.getByType<LibraryExtension>().apply {
            namespace = id
            compileSdk = 35

            defaultConfig {
                minSdk = 24
            }

            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_11
                targetCompatibility = JavaVersion.VERSION_11
            }
        }

        tasks.withType<KotlinCompile>().configureEach {
            compilerOptions {
                jvmTarget.set(JvmTarget.JVM_11)
            }
        }

        dependencies {
            add("implementation", libs.findLibrary("kotlin-coroutines-core").get())
            add("implementation", libs.findLibrary("kotlin-collections-immutable").get())
            add("implementation", libs.findLibrary("timber").get())
            add("testImplementation", libs.findLibrary("junit").get())
            add("testImplementation", libs.findLibrary("kotlin-coroutines-test").get())

            add("implementation", project(":project:core:utils"))
        }

        afterEvaluate {
            dependencies {
                extension.projectDependencies.forEach {
                    add("implementation", project(it))
                }
            }
        }
    }
}

// 🔹 DSL
abstract class DomainExtension(private val project: Project) {
    internal val projectDependencies = mutableListOf<String>()

    fun dependencies(block: DependencyHandler.() -> Unit) {
        DependencyHandler().apply(block)
    }

    inner class DependencyHandler {

        fun implementation(project: Project) {
            projectDependencies.add(project.path)
        }

        fun implementation(notation: String) {
            projectDependencies.add(notation)
        }
    }
}
