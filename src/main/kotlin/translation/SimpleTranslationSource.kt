package de.nycode.kampfire.translation

public class SimpleTranslationSource
    (translations: List<SimpleTranslation> = emptyList()) : TranslationSource<SimpleTranslation> {

    private val translations = translations.associateBy { it.translationKey }

    override suspend fun getTranslation(key: String): SimpleTranslation? {
        return translations[key]
    }
}
