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
        commonMain.dependencies {
            api(splitties("experimental"))
        }
        androidMain.dependencies {
            api(splitties("checkedlazy"))
            api(splitties("exceptions"))
            api(AndroidX.annotation)
            api(AndroidX.fragmentKtx)
            api(AndroidX.lifecycle.runtimeKtx)
            api(AndroidX.lifecycle.viewModelKtx)
            api(AndroidX.lifecycle.liveDataKtx)
        }
        all {
            languageSettings.apply {
                useExperimentalAnnotation("kotlin.RequiresOptIn")
            }
        }
    }
}
