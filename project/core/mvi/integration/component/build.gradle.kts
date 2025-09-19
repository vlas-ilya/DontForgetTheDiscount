plugins {
    alias(libs.plugins.kotlin.compose)
    id("su.tease.library")
}

library {
    withCompose()
    withKoin()

    dependencies {
        implementation(project(":project:core:mvi:api"))
        implementation(project(":project:core:mvi:impl"))
        implementation(project(":project:core:mvi:integration:navigation"))
        implementation(project(":project:core:navigation"))
        implementation(project(":project:core:utils"))
        implementation(project(":project:design:theme:api"))
        implementation(project(":project:design:component:controls"))
    }
}
