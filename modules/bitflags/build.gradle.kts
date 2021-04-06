/*
 * Copyright 2019-2020 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

plugins {
    kotlin("multiplatform")
    `maven-publish`
    id("org.jetbrains.dokka")
}

kotlin {
    jvm()
    js { browser(); nodejs() }

    macosX64()
    iosArm32(); iosArm64(); iosX64()
    watchosArm32(); watchosArm64(); watchosX86()

    linux(x64 = true)
    mingw(x64 = true)

    configure(targets) { configureMavenPublication() }
}

publishing {
    setupAllPublications(project)
}
