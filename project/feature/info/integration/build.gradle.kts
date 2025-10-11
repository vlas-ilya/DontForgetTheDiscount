plugins {
    id("su.tease.integration")
}

integration {
    dependencies {
        implementation(project(":project:feature:bank:presentation"))
        implementation(project(":project:feature:preset:presentation"))
    }
}
