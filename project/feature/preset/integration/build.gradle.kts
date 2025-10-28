plugins {
    id("su.tease.integration")
}

integration {
    dependencies {
        implementation(":project:feature:icon:presentation")
    }
}
