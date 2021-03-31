package de.nycode.kampfire.locales

import java.util.*

public data class KampfireLocale
@JvmOverloads constructor(
    public val language: String,
    public val languageCode: String,
    public val countryCode: String? = null
) {
    public fun toLocale(): Locale {
        return Locale(languageCode, countryCode)
    }
}
