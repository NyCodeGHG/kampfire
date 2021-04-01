package de.nycode.kampfire.extensions

import de.nycode.kampfire.translation.Translation
import net.kyori.adventure.audience.Audience

/**
 * Send a [Translation] to an [Audience]
 */
public fun Audience.sendTranslatedMessage(translation: Translation) {
    translation.sendMessage(this)
}
