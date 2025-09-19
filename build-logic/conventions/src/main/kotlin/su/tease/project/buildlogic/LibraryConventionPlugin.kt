package su.tease.project.buildlogic

import com.android.build.gradle.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.project
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.io.File
import org.gradle.api.provider.Provider

class LibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

        pluginManager.apply("com.android.library")
        pluginManager.apply("org.jetbrains.kotlin.android")
        pluginManager.apply("org.jetbrains.kotlin.plugin.parcelize")
        pluginManager.apply("org.jetbrains.kotlin.plugin.serialization")

        val extension = extensions.create("library", MyLibraryExtension::class.java, this)
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
        }

        afterEvaluate {
            if (extension.withCompose) {
                //pluginManager.apply("org.jetbrains.kotlin.plugin.compose")
                extensions.configure<LibraryExtension> {
                    buildFeatures {
                        compose = true
                    }

                    composeOptions {
                        kotlinCompilerExtensionVersion = "1.5.15"
                    }
                }
                dependencies {

                    // Compose
                    val composeBom = platform(libs.findLibrary("compose-bom").get())
                    add("implementation", composeBom)
                    add("implementation", libs.findLibrary("compose-material").get())
                    add("implementation", libs.findLibrary("compose-ui").get())
                    add("implementation", libs.findLibrary("compose-ui-tooling-preview").get())
                    add("implementation", libs.findLibrary("compose-material-adaptive").get())
                    add("debugImplementation", libs.findLibrary("compose-ui-tooling").get())
                    add("debugImplementation", libs.findLibrary("compose-ui-test-manifest").get())
                    add("androidTestImplementation", composeBom)
                    add("androidTestImplementation", libs.findLibrary("compose-ui-test").get())
                }
            }
            if (extension.withKoin) {
                dependencies {
                    // Architecture
                    add("implementation", libs.findLibrary("koin.core").get())
                    add("implementation", platform(libs.findLibrary("koin.bom").get()))
                }
            }
            if (extension.withNetwork) {
                dependencies {
                    // Network
                    add("implementation", libs.findLibrary("retrofit").get())
                    add("implementation", libs.findLibrary("retrofit-serialization").get())
                    add("implementation", libs.findLibrary("kotlin-serialization").get())
                    add("implementation", libs.findLibrary("okhttp3-logging").get())
                }
            }
            dependencies {
                extension.libraryDependencies.forEach {
                    add("implementation", it)
                }
                extension.projectDependencies.forEach {
                    add("implementation", project(it))
                }
            }
        }
    }
}

// 🔹 DSL
abstract class MyLibraryExtension(private val project: Project) {
    internal var withCompose: Boolean = false
    internal var withKoin: Boolean = false
    internal var withNetwork: Boolean = false
    internal val projectDependencies = mutableListOf<String>()
    internal val libraryDependencies = mutableListOf<String>()

    fun withCompose() {
        withCompose = true
    }

    fun withKoin() {
        withKoin = true
    }

    fun withNetwork() {
        withNetwork = true
    }

    fun dependencies(block: DependencyHandler.() -> Unit) {
        DependencyHandler().apply(block)
    }

    inner class DependencyHandler {

        fun implementation(project: Project) {
            projectDependencies.add(project.path)
        }

        fun implementation(provide: Provider<MinimalExternalModuleDependency>) {
            libraryDependencies.add("${provide.get().group}:${provide.get().name}:${provide.get().version}")
        }

        fun implementation(notation: String) {
            projectDependencies.add(notation)
        }
    }
}
