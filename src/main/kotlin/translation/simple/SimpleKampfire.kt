package de.nycode.kampfire.translation.simple

import de.nycode.kampfire.Kampfire
import de.nycode.kampfire.KampfireOptions
import de.nycode.kampfire.kampfire

@JvmName("simpleKampfire")
public fun kampfire(builder: KampfireOptions<SimpleTranslation, SimpleTranslationSource>.() -> Unit): Kampfire<SimpleTranslation, SimpleTranslationSource> =
    kampfire {
        translationSource = SimpleTranslationSource()
        builder()
    }
