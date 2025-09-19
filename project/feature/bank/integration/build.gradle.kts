plugins {
    id("su.tease.integration")
}

integration {
    dependencies {
        implementation(project(":project:feature:cashback:domain"))
        implementation(project(":project:feature:cashback:data"))
        implementation(project(":project:feature:cashback:presentation"))
        implementation(project(":project:feature:preset:domain"))
        implementation(project(":project:feature:preset:presentation"))
    }
}
