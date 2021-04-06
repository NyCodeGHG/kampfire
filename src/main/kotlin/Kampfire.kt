package de.nycode.kampfire

import de.nycode.kampfire.locales.KampfireLocale
import de.nycode.kampfire.locales.KampfireLocales
import de.nycode.kampfire.translation.Translation
import de.nycode.kampfire.translation.TranslationSource

/**
 * Central Kampfire class used for interacting with the api.
 * Constructor is internal. Use [kampfire] for creating a new instance.
 */
public class Kampfire<T : Translation, S : TranslationSource<T>> internal constructor(options: KampfireOptions<T, S>) {
    private val locales: List<KampfireLocale> = options.locales
    internal val translationSource: S =
        options.translationSource ?: error("Translation Source is missing!")

    private var fallbackLocale: KampfireLocale = options.fallbackLocale

    init {
        if (options.global && GlobalKampfire.kampfire != null) {
            error("There is already a global kampfire instance! Only one instance can be the global one.")
        } else {
            GlobalKampfire.kampfire = this
        }
    }

    public suspend fun getTranslation(locale: KampfireLocale, key: String): T? {
        return translationSource.getTranslation(locale, key)
            ?: translationSource.getTranslation(fallbackLocale, key)
    }

    public fun translate(key: String): TranslationContext<T, S> = TranslationContext(this, key)
}

public class KampfireOptions<T : Translation, S : TranslationSource<T>>(
    public var locales: List<KampfireLocale> = listOf(KampfireLocales.AMERICAN_ENGLISH, KampfireLocales.GERMAN),
    public var translationSource: S? = null,
    public var fallbackLocale: KampfireLocale = KampfireLocales.AMERICAN_ENGLISH
) {

    internal var global = false
    internal var initializeScope: S.() -> Unit = {}

    public fun global() {
        global = true
    }

    public fun initializeSource(scope: S.() -> Unit) {
        initializeScope = scope
    }
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
    return Kampfire(KampfireOptions<T, S>().apply(builder).also {
        it.translationSource?.let { source -> it.initializeScope(source) }
    })
}
