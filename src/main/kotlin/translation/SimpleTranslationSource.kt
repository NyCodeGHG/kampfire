package de.nycode.kampfire.translation

import de.nycode.kampfire.locales.KampfireLocale

public class SimpleTranslationSource
    (translations: List<SimpleTranslation> = emptyList()) : TranslationSource<SimpleTranslation> {

    private val translations = translations.associateBy { it.translationKey to it.locale }.toMutableMap()

    override suspend fun getTranslation(locale: KampfireLocale, key: String): SimpleTranslation? {
        return translations[key to locale]
    }

    public fun registerTranslation(translation: SimpleTranslation) {
        translations[translation.translationKey to translation.locale] = translation
    }

}
