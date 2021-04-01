package de.nycode.kampfire

import de.nycode.kampfire.translation.SimpleTranslation
import de.nycode.kampfire.translation.SimpleTranslationSource
import de.nycode.kampfire.translation.TranslationSource
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

        val (kampfire: Kampfire<SimpleTranslation>, source: SimpleTranslationSource)
                = createTestKampfire<SimpleTranslation, SimpleTranslationSource> { source ->
            coEvery { source.getTranslation(translationKey) } returns SimpleTranslation(
                translationKey,
                expected
            )
        }

        val translation = kampfire.getTranslation(translationKey)!!

        coVerify { source.getTranslation(translationKey) }
        confirmVerified(source)

        assertEquals(translation.translation, expected)
        assertEquals(translation.translationKey, translationKey)
    }

    @Test
    fun `Test Simple Kampfire Builder`() = runBlockingTest {
        val expected = "Hello World!"
        val translationKey = "testkey"

        val source = mockk<TranslationSource<SimpleTranslation>>()

        coEvery { source.getTranslation(translationKey) } returns SimpleTranslation(translationKey, expected)

        val kampfire = kampfire {
            translationSource = source
        }

        val (key, translation) = kampfire.getTranslation(translationKey)!!
        assertEquals(expected, translation)
        assertEquals(translationKey, key)
    }
}
