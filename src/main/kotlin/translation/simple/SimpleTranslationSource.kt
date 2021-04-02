package de.nycode.kampfire.translation.simple

import de.nycode.kampfire.locales.KampfireLocale
import de.nycode.kampfire.translation.TranslationSource

/**
 * Simple translation source with a map of translations and their associated locales
 */
public class SimpleTranslationSource
    (translations: List<SimpleTranslation> = emptyList()) : TranslationSource<SimpleTranslation> {

    private val translations = translations.associateBy { it.translationKey to it.locale }.toMutableMap()

    override suspend fun getTranslation(locale: KampfireLocale, key: String): SimpleTranslation? {
        return translations[key to locale]
    }

    /**
     * Register a [translation] on this source
     */
    public fun registerTranslation(translation: SimpleTranslation) {
        translations[translation.translationKey to translation.locale] = translation
    }

}
