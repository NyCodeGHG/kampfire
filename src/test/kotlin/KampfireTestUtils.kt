package de.nycode.kampfire

import de.nycode.kampfire.translation.Translation
import de.nycode.kampfire.translation.TranslationSource
import de.nycode.kampfire.translation.simple.SimpleTranslation
import de.nycode.kampfire.translation.simple.SimpleTranslationSource
import io.mockk.mockk

inline fun <T : Translation, reified S : TranslationSource<T>> createMockKampfire(sourceScope: (source: S) -> Unit = {}): Pair<Kampfire<T, S>, S> {
    val source = mockk<S>()
    sourceScope(source)
    return kampfire<T, S> {
        translationSource = source
    } to source
}

inline fun createSimpleKampfire(sourceScope: (source: SimpleTranslationSource) -> Unit = {}): Pair<Kampfire<SimpleTranslation, SimpleTranslationSource>, SimpleTranslationSource> {
    val source = SimpleTranslationSource()
    sourceScope(source)
    return kampfire<SimpleTranslation, SimpleTranslationSource> {
        translationSource = source
    } to source
}
