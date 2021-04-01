package de.nycode.kampfire

import de.nycode.kampfire.translation.Translation
import de.nycode.kampfire.translation.TranslationSource
import io.mockk.mockk

inline fun <T : Translation, reified S : TranslationSource<T>> createTestKampfire(sourceScope: (source: S) -> Unit = {}): Pair<Kampfire<T>, S> {
    val source = mockk<S>()
    sourceScope(source)
    return kampfire<T> {
        translationSource = source
    } to source
}
