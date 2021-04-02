package de.nycode.kampfire.sources

import de.nycode.kampfire.locales.KampfireLocales
import de.nycode.kampfire.translation.simple.kampfire
import de.nycode.kampfire.translation.simple.translatesTo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import kotlin.test.Test
import kotlin.test.assertEquals

object SimpleTranslationSourceTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Translation with 2 languages`() = runBlockingTest {
        val key = "testkey"
        val englishTranslation = "Hello World"
        val germanTranslation = "Hallo Welt"

        val kampfire = kampfire { }
        kampfire.translationSource.run {
            registerTranslation(key translatesTo englishTranslation withLanguage KampfireLocales.AMERICAN_ENGLISH)
            registerTranslation(key translatesTo germanTranslation withLanguage KampfireLocales.GERMAN)
        }

        val germanTranslationResult = kampfire.translate(key) toLanguage KampfireLocales.GERMAN
        val englishTranslationResult = kampfire.translate(key) toLanguage KampfireLocales.AMERICAN_ENGLISH
        assertEquals(englishTranslation, englishTranslationResult?.translation)
        assertEquals(germanTranslation, germanTranslationResult?.translation)
    }
}
