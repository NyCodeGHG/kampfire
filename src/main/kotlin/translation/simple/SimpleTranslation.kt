package de.nycode.kampfire.translation.simple

import de.nycode.kampfire.locales.KampfireLocale
import de.nycode.kampfire.translation.Translation
import net.kyori.adventure.audience.Audience
import net.kyori.adventure.text.Component

/**
 * Represents a simple translation containing a [translationKey] and the associated [translation]
 */
public data class SimpleTranslation(
    public val locale: KampfireLocale,
    public val translationKey: String,
    public val translation: String
) : Translation {
    override fun sendMessage(audience: Audience) {
        audience.sendMessage(toComponent())
    }

    public fun toComponent(): Component {
        return Component.text(translation)
    }
}
