package de.nycode.kampfire.translation

public class SimpleTranslationSource : TranslationSource<SimpleTranslation> {
    override suspend fun getTranslation(key: String): SimpleTranslation {
        return SimpleTranslation(key, "Test")
    }
}
