pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Don't Forget The Discount"
include(":project:app")
include(":project:feature:main")
include(":project:design:theme:impl")
include(":project:design:theme:api")
include(":project:core:mvi:api")
include(":project:core:mvi:impl")
