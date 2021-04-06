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
    js { browser(); nodejs() }

    macosX64()
    iosArm32(); iosArm64(); iosX64()
    watchosArm32(); watchosArm64(); watchosX86()

    configure(targets) { configureMavenPublication() }
    sourceSets {
        val darwinMain by creating {
            dependsOn(commonMain)
            val platforms = listOf("macos", "ios", "watchos", "tvos")
            filter { sourceSet ->
                sourceSet.name.endsWith("Main") &&
                    platforms.any { sourceSet.name.startsWith(it) }
            }.forEach { it.dependsOn(this) }
        }
        commonTest {
            dependencies {
                implementation(project(":test-helpers"))
            }
        }
    }
}

dependencies {
    androidTestImplementation(AndroidX.test.runner)
    testImplementation(Testing.roboElectric)
}
