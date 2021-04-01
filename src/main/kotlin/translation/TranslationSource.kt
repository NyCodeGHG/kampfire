package de.nycode.kampfire.translation

public interface TranslationSource<T : Translation> {

    public suspend fun getTranslation(key: String): T

}
