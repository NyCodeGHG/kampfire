package de.nycode.kampfire.translation.properties

import de.nycode.kampfire.locales.KampfireLocale
import de.nycode.kampfire.translation.TranslationSource
import de.nycode.kampfire.translation.simple.SimpleTranslation
import de.nycode.kampfire.translation.simple.translatesTo
import java.util.*

/**
 * This translation source is able to parse .properties files from the classpath and use it for [de.nycode.kampfire.Kampfire]
 */
public class PropertiesTranslationSource(
    locales: List<KampfireLocale>,
    classpathDirectory: String
) : TranslationSource<SimpleTranslation> {

    internal val translations = mutableMapOf<Pair<String, KampfireLocale>, SimpleTranslation>()

    init {
        locales.forEach { locale ->
            val fileName = "${locale.languageCode}_${locale.countryCode ?: ""}.properties"
            val resource = javaClass.classLoader.getResourceAsStream("$classpathDirectory/$fileName") ?: return@forEach
            val properties = Properties()
            try {
                properties.load(resource)
            } catch (exception: Exception) {
                exception.printStackTrace()
            } finally {
                resource.close()
            }
            properties.forEach { (key, value) ->
                key as String
                value as String
                translations[key to locale] = key translatesTo value withLanguage locale
            }
        }
    }

    override suspend fun getTranslation(locale: KampfireLocale, key: String): SimpleTranslation? {
        return translations[key to locale]
    }
}
