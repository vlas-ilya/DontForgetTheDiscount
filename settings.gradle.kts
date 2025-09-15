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

include(":project:feature:notification:api")
include(":project:feature:notification:impl")

include(":project:feature:bank:data")
include(":project:feature:bank:domain")
include(":project:feature:bank:integration")
include(":project:feature:bank:presentation")

include(":project:feature:cashback:data")
include(":project:feature:cashback:domain")
include(":project:feature:cashback:integration")
include(":project:feature:cashback:presentation")

include(":project:feature:info:data")
include(":project:feature:info:domain")
include(":project:feature:info:integration")
include(":project:feature:info:presentation")

include(":project:feature:preset:data")
include(":project:feature:preset:domain")
include(":project:feature:preset:integration")
include(":project:feature:preset:presentation")

include(":project:feature:shop:data")
include(":project:feature:shop:domain")
include(":project:feature:shop:integration")
include(":project:feature:shop:presentation")

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
