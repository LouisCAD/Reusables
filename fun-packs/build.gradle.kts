/*
 * Copyright 2020 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

plugins {
    id("com.gradleup.auto.manifest")
}

autoManifest {
    packageName.set("splitties.fun.pack")
    replaceDashesWithDot.set(true)
}
