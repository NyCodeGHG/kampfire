package de.nycode.kampfire.extensions

import de.nycode.kampfire.GlobalKampfire
import de.nycode.kampfire.Kampfire
import de.nycode.kampfire.locales.KampfireLocale
import de.nycode.kampfire.translation.Translation
import de.nycode.kampfire.translation.TranslationSource
import net.kyori.adventure.audience.Audience

/**
 * Send a [Translation] to an [Audience]
 */
public fun Audience.sendTranslatedMessage(translation: Translation) {
    translation.sendMessage(this)
}

public suspend fun <T : Translation, S : TranslationSource<T>> Audience.sendTranslatedMessage(
    kampfire: Kampfire<T, S>,
    key: String,
    locale: KampfireLocale
) {
    kampfire.getTranslation(locale, key)?.sendMessage(this)
}

/**
 * Send Translation via the global Kampfire instance
 */
public suspend fun <T : Translation, S : TranslationSource<T>> Audience.sendTranslatedMessage(
    key: String,
    locale: KampfireLocale
) {
    require(GlobalKampfire.kampfire != null) { "Global kampfire can't be null" }
    GlobalKampfire.kampfire?.getTranslation(locale, key)?.sendMessage(this)
}
