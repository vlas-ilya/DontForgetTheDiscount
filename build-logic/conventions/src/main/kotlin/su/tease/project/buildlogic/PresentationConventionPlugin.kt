package su.tease.project.buildlogic

import com.android.build.gradle.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.project
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.io.File


class PresentationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

        // плагины
        pluginManager.apply("com.android.library")
        pluginManager.apply("org.jetbrains.kotlin.android")
        pluginManager.apply("org.jetbrains.kotlin.plugin.compose")
        pluginManager.apply("org.jetbrains.kotlin.plugin.parcelize")
        pluginManager.apply("org.jetbrains.kotlin.plugin.serialization")

        // DSL
        val extension = extensions.create("presentation", PresentationExtension::class.java, this)
        val path = project.projectDir.toRelativeString(project.rootDir).split(File.separator)
        val id = path.joinToString(".", "su.tease.")
        val domainModule = path.dropLast(1).joinToString(":", ":", ":domain")

        // android конфигурация
        extensions.configure<LibraryExtension> {
            namespace = id
            compileSdk = 35

            defaultConfig {
                minSdk = 24
                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                consumerProguardFiles("consumer-rules.pro")
            }

            buildFeatures {
                compose = true
            }

            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_11
                targetCompatibility = JavaVersion.VERSION_11
            }

            composeOptions {
                kotlinCompilerExtensionVersion = "1.5.15"
            }
        }

        tasks.withType<KotlinCompile>().configureEach {
            compilerOptions {
                jvmTarget.set(JvmTarget.JVM_11)
            }
        }

        // базовые зависимости
        dependencies {
            // core
            add("implementation", libs.findLibrary("kotlin-collections-immutable").get())
            add("implementation", libs.findLibrary("kotlin-coroutines-core").get())
            add("implementation", libs.findLibrary("timber").get())
            add("testImplementation", libs.findLibrary("junit").get())
            add("testImplementation", libs.findLibrary("kotlin-coroutines-test").get())

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

            // project defaults
            add("implementation", project(":project:design:icons"))
            add("implementation", project(":project:design:component:controls"))
            add("implementation", project(":project:design:theme:api"))
            add("implementation", project(":project:core:clean"))
            add("implementation", project(":project:core:mvi:api"))
            add("implementation", project(":project:core:mvi:integration:clean"))
            add("implementation", project(":project:core:mvi:integration:component"))
            add("implementation", project(":project:core:mvi:integration:navigation"))
            add("implementation", project(":project:core:mvi:middleware:intercept"))
            add("implementation", project(":project:core:mvi:middleware:suspend"))
            add("implementation", project(":project:core:navigation"))
            add("implementation", project(":project:core:utils"))
            add("implementation", project(":project:feature:notification:api"))

            // domain
            add("implementation", project(domainModule))
        }

        afterEvaluate {
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
abstract class PresentationExtension(private val project: Project) {
    internal val projectDependencies = mutableListOf<String>()
    internal val libraryDependencies = mutableListOf<String>()

    fun dependencies(block: DependencyHandler.() -> Unit) {
        DependencyHandler().apply(block)
    }

    inner class DependencyHandler {
        fun implementation(notation: String) {
            projectDependencies.add(notation)
        }

        fun implementation(provide: Provider<MinimalExternalModuleDependency>) {
            libraryDependencies.add("${provide.get().group}:${provide.get().name}:${provide.get().version}")
        }

        fun implementation(project: Project) {
            projectDependencies.add(project.path)
        }
    }
}
