plugins {
    id("su.tease.integration")
}

integration {
    dependencies {
        implementation(project(":project:feature:preset:domain"))
        implementation(project(":project:feature:preset:presentation"))
        implementation(project(":project:feature:bank:domain"))
        implementation(project(":project:feature:bank:presentation"))
        implementation(project(":project:feature:shop:domain"))
        implementation(project(":project:feature:shop:presentation"))
    }
}
