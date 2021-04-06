/*
 * Copyright 2019-2020 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("SpellCheckingInspection")

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
            api(project(":fun-packs:android-base"))
            listOf(
                "alertdialog-appcompat",
                "alertdialog-appcompat-coroutines",
                "views-appcompat",
                "views-selectable-appcompat"
            ).forEach { api(splitties(it)) }
        }
    }
}
