package de.nycode.kampfire.extensions

import de.nycode.kampfire.translation.Translation
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import net.kyori.adventure.audience.Audience
import kotlin.test.Test

object AudienceTest {

    @Test
    fun `Send Message to Audience`() {
        val audience = mockk<Audience>()
        val translation = mockk<Translation>()

        every { translation.sendMessage(audience) } returns Unit

        audience.sendTranslatedMessage(translation)

        verify { translation.sendMessage(audience) }
        confirmVerified(translation)
    }

}
