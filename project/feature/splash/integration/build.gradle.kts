plugins {
    id("su.tease.integration")
}

integration {
    dependencies {
        implementation(project(":project:feature:main:integration"))
        implementation(project(":project:feature:preset:domain"))
    }
}
