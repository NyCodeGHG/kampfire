package de.nycode.kampfire.translation

import de.nycode.kampfire.locales.KampfireLocale

public data class SimpleTranslationBuilder(val key: String, val translation: String) {
    public infix fun withLanguage(locale: KampfireLocale): SimpleTranslation {
        return SimpleTranslation(locale, key, translation)
    }
}

public infix fun String.translatesTo(translation: String): SimpleTranslationBuilder {
    return SimpleTranslationBuilder(this, translation)
}
