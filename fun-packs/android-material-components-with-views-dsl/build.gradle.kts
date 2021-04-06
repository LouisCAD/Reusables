@file:Suppress("SpellCheckingInspection")

/*
* Copyright 2019-2020 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
*/

plugins {
    id("com.android.library")
    kotlin("multiplatform")
    publish
}

android {
    setDefaults()
}

kotlin {
    android()

    configure(targets) { configureMavenPublication() }
    sourceSets {
        androidMain.dependencies {
            api(project(":fun-packs:android-material-components"))
            api(project(":fun-packs:android-appcompat-with-views-dsl"))
            listOf(
                "views-dsl-coordinatorlayout",
                "views-dsl-material"
            ).forEach { api(splitties(it)) }
        }
    }
}
