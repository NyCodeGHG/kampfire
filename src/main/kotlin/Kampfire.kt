package de.nycode.kampfire

import de.nycode.kampfire.locales.KampfireLocale
import de.nycode.kampfire.locales.KampfireLocales
import de.nycode.kampfire.translation.SimpleTranslation
import de.nycode.kampfire.translation.SimpleTranslationSource
import de.nycode.kampfire.translation.Translation
import de.nycode.kampfire.translation.TranslationSource

public class KampfireOptions<T : Translation>(
    public var locales: List<KampfireLocale> = listOf(KampfireLocales.AMERICAN_ENGLISH, KampfireLocales.GERMAN),
    internal var translationSource: TranslationSource<T>? = null
)

public class Kampfire<T : Translation> constructor(options: KampfireOptions<T>) {
    internal val locales: List<KampfireLocale> = options.locales
    internal val translationSource: TranslationSource<T> = options.translationSource ?: error("Translation Source is missing!")

    public suspend fun getTranslation(key: String): T {
        return translationSource.getTranslation(key)
    }
}

public fun <T : Translation> kampfire(builder: KampfireOptions<T>.() -> Unit): Kampfire<T> {
    return Kampfire(KampfireOptions<T>().apply(builder))
}

@JvmName("simpleKampfire")
public fun kampfire(builder: KampfireOptions<SimpleTranslation>.() -> Unit): Kampfire<SimpleTranslation> =
    kampfire<SimpleTranslation> {
        builder()
        translationSource = SimpleTranslationSource()
    }
