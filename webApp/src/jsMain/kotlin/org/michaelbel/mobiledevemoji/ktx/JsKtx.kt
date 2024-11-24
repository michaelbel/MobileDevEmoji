package org.michaelbel.mobiledevemoji.ktx

import kotlinx.browser.document
import org.w3c.dom.HTMLInputElement

actual fun isMobileBrowser(): Boolean {
    val userAgent = js("navigator.userAgent") as String
    return userAgent.contains("Mobile") || userAgent.contains("Android") || userAgent.contains("iPhone")
}

actual fun showKeyboard() {
    val inputElement = document.querySelector("input") as? HTMLInputElement
    inputElement?.focus()
}