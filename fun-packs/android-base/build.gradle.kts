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
            listOf(
                "activities",
                "appctx",
                "bitflags",
                "bundle",
                "collections",
                "coroutines",
                "dimensions",
                "fragments",
                "fragmentargs",
                "intents",
                "lifecycle-coroutines",
                "mainhandler",
                "mainthread",
                "material-colors",
                "permissions",
                "preferences",
                "resources",
                "systemservices",
                "toast",
                "views",
                "views-coroutines",
                "views-recyclerview",
                "views-selectable",
                "views-selectable-constraintlayout"
            ).forEach { api(splitties(it)) }
        }
    }
}
