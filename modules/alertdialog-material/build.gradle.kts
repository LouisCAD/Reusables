/*
 * Copyright 2019-2020 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

plugins {
    id("com.android.library")
    kotlin("multiplatform")
    `maven-publish`
}

android {
    setDefaults()
}

kotlin {
    android()

    configure(targets) { configureMavenPublication() }
    sourceSets {
        commonMain.dependencies {
            api(splitties("experimental"))
            api(splitties("alertdialog-appcompat"))
        }
        androidMain.dependencies {
            api(Google.android.material)
        }
    }
}

afterEvaluate {
    publishing {
        setupAllPublications(project)
    }
}
