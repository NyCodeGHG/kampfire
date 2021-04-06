package de.nycode.kampfire.extensions

import de.nycode.kampfire.locales.KampfireLocale
import java.util.*

@JvmName("fromLocale")
public fun Locale.toKampfireLocale(): KampfireLocale {
    return KampfireLocale(displayLanguage, language, country)
}
