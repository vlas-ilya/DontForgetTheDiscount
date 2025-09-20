plugins {
    id("su.tease.integration")
}

integration {
    dependencies {
        implementation(project(":project:design:icons"))
        implementation(project(":project:design:component:controls"))
        implementation(project(":project:design:component:navigation_bar"))
        implementation(project(":project:design:theme:impl"))
        implementation(project(":project:design:theme:api"))
        implementation(project(":project:core:mvi:api"))
        implementation(project(":project:core:mvi:integration:component"))
        implementation(project(":project:core:mvi:integration:navigation"))
        implementation(project(":project:core:navigation"))

        implementation(project(":project:feature:notification:api"))
        implementation(project(":project:feature:notification:impl"))
        implementation(project(":project:feature:bank:presentation"))
        implementation(project(":project:feature:shop:presentation"))
        implementation(project(":project:feature:info:presentation"))
    }
}
