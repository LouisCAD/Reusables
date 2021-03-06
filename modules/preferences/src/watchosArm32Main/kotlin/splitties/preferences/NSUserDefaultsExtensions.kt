/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.preferences

import platform.Foundation.NSNumber
import platform.Foundation.NSUserDefaults

internal actual fun NSUserDefaults.setLong(value: Long, forKey: String) {
    setObject(value = NSNumber(longLong = value), forKey = forKey)
}

internal actual fun NSUserDefaults.setInt(value: Int, forKey: String) {
    setInteger(value = value, forKey = forKey)
}

internal actual fun NSUserDefaults.longForKey(key: String): Long {
    return (objectForKey(key) as NSNumber).longLongValue
}

internal actual fun NSUserDefaults.intForKey(key: String): Int = integerForKey(key)
