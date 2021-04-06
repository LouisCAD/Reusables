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
    jvm()
    js { browser(); nodejs() }

    macosX64()
    iosArm32(); iosArm64(); iosX64()
    watchosArm32(); watchosArm64(); watchosX86()

    linux(x64 = true)
    mingw(x64 = true)

    sourceSets {
        commonMain.dependencies {
            api(Kotlin.test.common)
            api(Kotlin.test.annotationsCommon)
            api(KotlinX.coroutines.core)
        }
        val allButJvmMain by creating {
            dependsOn(commonMain)
            jsMain.dependsOn(this)
        }
        val allJvmMain by creating {
            dependsOn(commonMain)
            androidMain.dependsOn(this)
            jvmMain.dependsOn(this)
            dependencies {
                api(Kotlin.test.junit)
            }
        }
        val nativeMain by creating {
            dependsOn(allButJvmMain)
            val platforms = listOf("linux", "mingw", "macos", "ios", "watchos", "tvos")
            filter { sourceSet ->
                sourceSet.name.endsWith("Main") &&
                    platforms.any { sourceSet.name.startsWith(it) }
            }.forEach { it.dependsOn(this) }
        }
        androidMain.dependencies {
            api(KotlinX.coroutines.android)
            api(AndroidX.test.ext.junit)
        }
        all {
            languageSettings.apply {
                useExperimentalAnnotation("kotlin.RequiresOptIn")
            }
        }
    }
}
