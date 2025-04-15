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
include(":project:feature:splash")
include(":project:design:component:navigation_bar")
include(":project:design:icons")
include(":project:design:theme:impl")
include(":project:design:theme:api")
include(":project:core:mvi:api")
include(":project:core:mvi:impl")
include(":project:core:mvi_android")
include(":project:core:miv_component")
include(":project:core:mvi_navigation")
include(":project:core:navigation")
include(":project:core:utils")
