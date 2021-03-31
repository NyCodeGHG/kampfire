package de.nycode.kampfire

import de.nycode.kampfire.locales.KampfireLocale
import de.nycode.kampfire.locales.KampfireLocales

public class KampfireOptions(
    public var classPathDirectory: String = "translations",
    public var locales: List<KampfireLocale> = listOf(KampfireLocales.AMERICAN_ENGLISH, KampfireLocales.GERMAN)
)

public class Kampfire constructor(options: KampfireOptions) {
    private val classPathDirectory: String = options.classPathDirectory
    private val locales: List<KampfireLocale> = options.locales
}

public fun Kampfire(builder: KampfireOptions.() -> Unit): Kampfire {
    return Kampfire(KampfireOptions().apply(builder))
}
