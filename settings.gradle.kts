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
include(":project:feature:cacheback")
include(":project:feature:notification:api")
include(":project:feature:notification:impl")
include(":project:feature:splash")
include(":project:design:component:navigation_bar")
include(":project:design:component:controls")
include(":project:design:icons")
include(":project:design:theme:impl")
include(":project:design:theme:api")
include(":project:core:clean")
include(":project:core:mvi:api")
include(":project:core:mvi:impl")
include(":project:core:mvi:integration:android")
include(":project:core:mvi:integration:clean")
include(":project:core:mvi:integration:component")
include(":project:core:mvi:integration:navigation")
include(":project:core:mvi:middleware:intercept")
include(":project:core:mvi:middleware:suspend")
include(":project:core:navigation")
include(":project:core:utils")
