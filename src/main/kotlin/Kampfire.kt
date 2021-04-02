package de.nycode.kampfire

import de.nycode.kampfire.locales.KampfireLocale
import de.nycode.kampfire.locales.KampfireLocales
import de.nycode.kampfire.translation.SimpleTranslation
import de.nycode.kampfire.translation.SimpleTranslationSource
import de.nycode.kampfire.translation.Translation
import de.nycode.kampfire.translation.TranslationSource

public class KampfireOptions<T : Translation, S : TranslationSource<T>>(
    public var locales: List<KampfireLocale> = listOf(KampfireLocales.AMERICAN_ENGLISH, KampfireLocales.GERMAN),
    public var translationSource: S? = null
)

public class Kampfire<T : Translation, S : TranslationSource<T>> constructor(options: KampfireOptions<T, S>) {
    internal val locales: List<KampfireLocale> = options.locales
    internal val translationSource: S =
        options.translationSource ?: error("Translation Source is missing!")

    public suspend fun getTranslation(locale: KampfireLocale, key: String): T? {
        return translationSource.getTranslation(locale, key)
    }

    public fun translate(key: String): TranslationContext<T, S> = TranslationContext(this, key)
}

public data class TranslationContext<T : Translation, S : TranslationSource<T>>(
    val kampfire: Kampfire<T, S>,
    val key: String
) {
    public suspend infix fun toLanguage(locale: KampfireLocale): T? {
        return kampfire.getTranslation(locale, key)
    }
}

public fun <T : Translation, S : TranslationSource<T>> kampfire(builder: KampfireOptions<T, S>.() -> Unit): Kampfire<T, S> {
    return Kampfire(KampfireOptions<T, S>().apply(builder))
}

@JvmName("simpleKampfire")
public fun kampfire(builder: KampfireOptions<SimpleTranslation, SimpleTranslationSource>.() -> Unit): Kampfire<SimpleTranslation, SimpleTranslationSource> =
    kampfire<SimpleTranslation, SimpleTranslationSource> {
        translationSource = SimpleTranslationSource()
        builder()
    }
