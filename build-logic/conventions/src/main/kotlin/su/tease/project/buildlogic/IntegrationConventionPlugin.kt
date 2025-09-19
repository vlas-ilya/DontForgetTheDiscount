package su.tease.project.buildlogic

import com.android.build.gradle.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.project
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.io.File

class IntegrationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

        // плагины
        pluginManager.apply("com.android.library")
        pluginManager.apply("org.jetbrains.kotlin.android")
        pluginManager.apply("org.jetbrains.kotlin.plugin.compose")
        pluginManager.apply("org.jetbrains.kotlin.plugin.parcelize")
        pluginManager.apply("org.jetbrains.kotlin.plugin.serialization")
        pluginManager.apply("com.google.devtools.ksp")
        pluginManager.apply("androidx.room")

        // DSL
        val extension = extensions.create("integration", IntegrationExtension::class.java, this)
        val path = project.projectDir.toRelativeString(project.rootDir).split(File.separator)
        val id = path.joinToString(".", "su.tease.")
        val dataModule = path.dropLast(1).joinToString(":", ":", ":data")
        val domainModule = path.dropLast(1).joinToString(":", ":", ":domain")
        val presentationModule = path.dropLast(1).joinToString(":", ":", ":presentation")

        extensions.getByType<LibraryExtension>().apply {
            namespace = id
            compileSdk = 35

            defaultConfig {
                minSdk = 24
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

        // kotlin { ... }
        tasks.withType<KotlinCompile>().configureEach {
            compilerOptions {
                jvmTarget.set(JvmTarget.JVM_11)
            }
        }

        // Room schemaDirectory
        val schemaDir = File(rootDir, "schemas")
        extensions.findByName("room")?.let { roomExt ->
            try {
                val methods = roomExt.javaClass.methods.filter { it.name == "schemaDirectory" }
                val methodString = methods.firstOrNull { it.parameterCount == 1 && it.parameterTypes[0] == String::class.java }
                val methodFile = methods.firstOrNull { it.parameterCount == 1 && it.parameterTypes[0] == File::class.java }

                when {
                    methodString != null -> methodString.invoke(roomExt, schemaDir.absolutePath)
                    methodFile != null -> methodFile.invoke(roomExt, schemaDir)
                    else -> logger.warn("Room extension doesn't expose schemaDirectory(String|File) — skipping schema config")
                }
            } catch (ex: Exception) {
                logger.warn("Failed to configure Room schemaDirectory: ${ex.message}")
            }
        }

        // зависимости
        dependencies {
            // Architecture
            add("implementation", platform(libs.findLibrary("koin.bom").get()))
            add("implementation", libs.findLibrary("koin.core").get())

            // Network
            add("implementation", libs.findLibrary("retrofit").get())

            // Core
            add("implementation", libs.findLibrary("kotlin-collections-immutable").get())
            add("implementation", libs.findLibrary("kotlin-coroutines-core").get())
            add("implementation", libs.findLibrary("timber").get())
            add("testImplementation", libs.findLibrary("junit").get())
            add("testImplementation", libs.findLibrary("kotlin-coroutines-test").get())

            // Database
            add("ksp", libs.findLibrary("room.compiler").get())
            add("annotationProcessor", libs.findLibrary("room.compiler").get())
            add("implementation", libs.findLibrary("room.ktx").get())
            add("debugImplementation", libs.findLibrary("room.testing").get())

            // Compose
            val composeBom = platform(libs.findLibrary("compose.bom").get())
            add("implementation", composeBom)
            add("implementation", libs.findLibrary("compose.material").get())
            add("implementation", libs.findLibrary("compose.ui").get())
            add("implementation", libs.findLibrary("compose.ui-tooling-preview").get())
            add("implementation", libs.findLibrary("compose.material-adaptive").get())
            add("debugImplementation", libs.findLibrary("compose.ui-tooling").get())
            add("debugImplementation", libs.findLibrary("compose.ui-test-manifest").get())
            add("androidTestImplementation", composeBom)
            add("androidTestImplementation", libs.findLibrary("compose.ui-test").get())

            // project
            add("implementation", project(":project:core:mvi:api"))
            add("implementation", project(":project:core:mvi:integration:clean"))
            add("implementation", project(":project:core:mvi:integration:component"))
            add("implementation", project(":project:core:mvi:integration:navigation"))
            add("implementation", project(":project:core:navigation"))
            add("implementation", project(":project:core:utils"))
            add("implementation", project(":project:design:theme:api"))
            add("implementation", project(":project:design:component:controls"))

            add("implementation", project(domainModule))
            add("implementation", project(dataModule))
            add("implementation", project(presentationModule))
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
abstract class IntegrationExtension(private val project: Project) {
    internal val projectDependencies = mutableListOf<String>()
    internal val libraryDependencies = mutableListOf<String>()

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
