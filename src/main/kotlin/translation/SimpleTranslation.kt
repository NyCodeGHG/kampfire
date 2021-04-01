package de.nycode.kampfire.translation

import net.kyori.adventure.audience.Audience
import net.kyori.adventure.text.Component

/**
 * Represents a simple translation containing a [translationKey] and the associated [translation]
 */
public data class SimpleTranslation(public val translationKey: String, public val translation: String) : Translation {
    override fun sendMessage(audience: Audience) {
        audience.sendMessage(Component.text(translation))
    }
}
