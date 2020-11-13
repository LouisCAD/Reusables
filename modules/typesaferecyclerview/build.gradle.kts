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
    sourceSets.getByName("main").java.srcDir("src/androidMain/java")
}

kotlin {
    android()

    configure(targets) { configureMavenPublication() }
    sourceSets {
        androidMain.dependencies {
            api(splitties("views-recyclerview"))
            api(AndroidX.annotation)
            api(AndroidX.recyclerView)
        }
    }
}

afterEvaluate {
    publishing {
        setupAllPublications(project)
    }
}
