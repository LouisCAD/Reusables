plugins {
    `maven-publish`
    id("org.jetbrains.dokka")
}

publishing {
    setupAllPublications(project)
}
