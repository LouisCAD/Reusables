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
    buildTypes.getByName("release").consumerProguardFiles("proguard-rules.pro")
}

kotlin {
    android()

    macosX64()
    iosArm32(); iosArm64(); iosX64()
    watchosArm32(); watchosArm64(); watchosX86()

    configure(targets) { configureMavenPublication() }
    sourceSets {
        commonMain.dependencies {
            api(splitties("experimental"))
            api(KotlinX.coroutines.core)
        }
        val allButAndroidMain by creating {
            dependsOn(commonMain)
        }
        val darwinMain by creating {
            dependsOn(allButAndroidMain)
            val platforms = listOf("macos", "ios", "watchos", "tvos")
            filter { sourceSet ->
                sourceSet.name.endsWith("Main") &&
                    platforms.any { sourceSet.name.startsWith(it) }
            }.forEach { it.dependsOn(this) }

            dependencies {
                implementation(splitties("mainthread"))
            }
        }
        androidMain.dependencies {
            api(splitties("appctx"))
            compileOnly(KotlinX.coroutines.android)
        }
        all {
            languageSettings.apply {
                useExperimentalAnnotation("kotlin.RequiresOptIn")
            }
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
