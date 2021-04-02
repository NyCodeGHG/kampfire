package de.nycode.kampfire.translation

import de.nycode.kampfire.locales.KampfireLocale

public interface TranslationSource<T : Translation> {

    public suspend fun getTranslation(locale: KampfireLocale, key: String): T?

}
