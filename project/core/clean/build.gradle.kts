plugins {
    id("su.tease.library")
}

library {
    dependencies {
        implementation(project(":project:core:utils"))
    }
}
