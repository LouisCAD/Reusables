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
            api(AndroidX.core.ktx)
            api(KotlinX.coroutines.android)
        }
        getByName("androidTest").dependencies {
            implementation(Kotlin.test.junit)
            implementation(AndroidX.test.coreKtx)
            implementation(AndroidX.test.runner)
            implementation(AndroidX.test.ext.junit)
            implementation(AndroidX.test.espresso.core)
            implementation(KotlinX.coroutines.android)
        }
        all {
            languageSettings.apply {
                useExperimentalAnnotation("kotlin.RequiresOptIn")
            }
        }
    }
}
