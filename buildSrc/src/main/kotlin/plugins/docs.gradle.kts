import org.jetbrains.dokka.gradle.DokkaMultiModuleTask
import org.jetbrains.dokka.gradle.DokkaTask

plugins {
    id("org.jetbrains.dokka")
}

tasks {
    withType<DokkaTask>() {
        dokkaSourceSets.configureEach {
            includes.from("README.md")
        }
    }
}
