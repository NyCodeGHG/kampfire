package de.nycode.kampfire

import de.nycode.kampfire.translation.SimpleTranslation
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
        val source = mockk<TranslationSource<SimpleTranslation>>()

        val expected = "Hello World!"
        val translationKey = "testkey"

        coEvery { source.getTranslation(translationKey) } returns SimpleTranslation(
            translationKey,
            expected
        )

        val kampfire: Kampfire<SimpleTranslation> = kampfire<SimpleTranslation> {
            translationSource = source
        }

        val translation = kampfire.getTranslation(translationKey)

        coVerify { source.getTranslation(translationKey) }
        confirmVerified(source)

        assertEquals(translation.translation, expected)
        assertEquals(translation.translationKey, translationKey)
    }
}
