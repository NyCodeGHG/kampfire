package de.nycode.kampfire

import de.nycode.kampfire.locales.KampfireLocales.AMERICAN_ENGLISH
import de.nycode.kampfire.locales.KampfireLocales.GERMAN
import de.nycode.kampfire.translation.TranslationSource
import de.nycode.kampfire.translation.simple.SimpleTranslation
import de.nycode.kampfire.translation.simple.SimpleTranslationSource
import de.nycode.kampfire.translation.simple.kampfire
import de.nycode.kampfire.translation.simple.translatesTo
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
object KampfireTest : CoroutineScope by TestCoroutineScope() {

    @Test
    fun `Test Kampfire Builder`() = runBlockingTest {
        val expected = "Hello World!"
        val translationKey = "testkey"

        val (kampfire: Kampfire<SimpleTranslation, SimpleTranslationSource>, source: SimpleTranslationSource)
                = createMockKampfire<SimpleTranslation, SimpleTranslationSource> { source ->
            coEvery {
                source.getTranslation(
                    AMERICAN_ENGLISH,
                    translationKey
                )
            } returns SimpleTranslation(
                AMERICAN_ENGLISH,
                translationKey,
                expected
            )
        }

        val translation = kampfire.getTranslation(AMERICAN_ENGLISH, translationKey)!!

        coVerify { source.getTranslation(AMERICAN_ENGLISH, translationKey) }
        confirmVerified(source)

        assertEquals(translation.translation, expected)
        assertEquals(translation.translationKey, translationKey)
    }

    @Test
    fun `Test Simple Kampfire Builder`() = runBlockingTest {
        val expected = "Hello World!"
        val translationKey = "testkey"

        val source = mockk<TranslationSource<SimpleTranslation>>()

        coEvery { source.getTranslation(AMERICAN_ENGLISH, translationKey) } returns SimpleTranslation(
            AMERICAN_ENGLISH,
            translationKey,
            expected
        )

        val kampfire = kampfire<SimpleTranslation, TranslationSource<SimpleTranslation>> {
            translationSource = source
        }

        val (_, key, translation) = kampfire.getTranslation(AMERICAN_ENGLISH, translationKey)!!
        assertEquals(expected, translation)
        assertEquals(translationKey, key)
    }

    @Test
    fun `Fallback to American English`() = runBlockingTest {
        val (kampfire, _) = createSimpleKampfire {
            it.registerTranslation("testkey" translatesTo "test_us" withLanguage AMERICAN_ENGLISH)
        }
        val translation = kampfire.getTranslation(GERMAN, "testkey")
        assertEquals("test_us", translation?.translation)
    }

    @Test
    fun `Fallback to other Language`() = runBlockingTest {
        val kampfire = kampfire {
            fallbackLocale = GERMAN
            initializeSource {
                registerTranslation("testkey" translatesTo "test_de" withLanguage GERMAN)
            }
        }
        val translation = kampfire.getTranslation(AMERICAN_ENGLISH, "testkey")
        assertEquals("test_de", translation?.translation)
    }
}
