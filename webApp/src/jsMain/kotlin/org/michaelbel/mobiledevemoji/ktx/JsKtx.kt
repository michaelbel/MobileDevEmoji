package org.michaelbel.mobiledevemoji.ktx

actual fun isMobileBrowser(): Boolean {
    val userAgent = js("navigator.userAgent") as String
    return userAgent.contains("Mobile") || userAgent.contains("Android") || userAgent.contains("iPhone")
}
