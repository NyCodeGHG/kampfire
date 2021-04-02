package de.nycode.kampfire.sources

import de.nycode.kampfire.kampfire
import de.nycode.kampfire.locales.KampfireLocales
import de.nycode.kampfire.translation.properties.PropertiesTranslationSource
import de.nycode.kampfire.translation.simple.SimpleTranslation
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
object PropertiesTranslationTest {

    @Test
    fun `Properties Reading`() = runBlockingTest {
        val locale = KampfireLocales.GERMAN
        val kampfire = kampfire<SimpleTranslation, PropertiesTranslationSource> {
            translationSource = PropertiesTranslationSource(
                listOf(locale),
                "translations"
            )
        }
        val expected = "Hallo Test!"
        val key = "test.key"
        val result = kampfire.getTranslation(locale, key)
        assertEquals(expected, result?.translation)
        assertEquals(key, result?.translationKey)
        assertEquals(locale, result?.locale)
        assertEquals(kampfire.translationSource.translations.size, 1)
    }

    @Test
    fun `Properties with 2 Language`() = runBlockingTest {
        val locales = listOf(KampfireLocales.AMERICAN_ENGLISH, KampfireLocales.GERMAN)
        val kampfire = kampfire<SimpleTranslation, PropertiesTranslationSource> {
            translationSource = PropertiesTranslationSource(locales, "translations")
        }

        val key = "test.key"
        val germanTranslation = kampfire.getTranslation(KampfireLocales.GERMAN, key)
        val englishTranslation = kampfire.getTranslation(KampfireLocales.AMERICAN_ENGLISH, key)
        assertEquals("Hallo Test!", germanTranslation?.translation)
        assertEquals("Hello Test!", englishTranslation?.translation)
        assertEquals(key, germanTranslation?.translationKey)
        assertEquals(key, englishTranslation?.translationKey)
    }

}
