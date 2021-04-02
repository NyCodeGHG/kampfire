package de.nycode.kampfire.translation.simple

import de.nycode.kampfire.locales.KampfireLocale

public data class SimpleTranslationBuilder(val key: String, val translation: String) {
    /**
     * Specify the language used for the translation in this builder
     */
    public infix fun withLanguage(locale: KampfireLocale): SimpleTranslation {
        return SimpleTranslation(locale, key, translation)
    }
}

/**
 * Create a new translation builder
 */
public infix fun String.translatesTo(translation: String): SimpleTranslationBuilder {
    return SimpleTranslationBuilder(this, translation)
}
