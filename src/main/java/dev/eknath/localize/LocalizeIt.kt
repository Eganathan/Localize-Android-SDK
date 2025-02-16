package dev.eknath.localize

import android.app.Application
import android.content.Context
import android.os.Build
import android.util.Log

object LocalizeIt {
    lateinit var supportedLocales: List<String>

    fun init(context: Application) {
        supportedLocales = context.resources.assets.locales.toString().replaceFirst("[", "").replaceAfterLast("]", "").split(",")

        ensureAllTranslationExists(context)
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU)
            setLocale(context = context, "tx")
    }

    fun setLocale(context: Context, languageCode: String) {
        if (!supportedLocales.contains(languageCode))
            throw IllegalArgumentException("Invalid Language Code : $languageCode, defined supported languages are $supportedLocales")

        Language.setLocale(context, languageCode)
    }


    fun ensureAllTranslationExists(context: Context){
        getAllStringResourceNames(context).forEach {
            Log.e("Test","T:$it")
        }
    }

    private fun getAllStringResourceNames(context: Context): List<String> {
        val resources = context.resources
        val packageName = context.packageName
        val rClass = Class.forName("$packageName.R\$string")

        return rClass.fields.mapNotNull { field ->
            try {
                val resId = field.getInt(null) // Get resource ID
                resources.getString(resId) // Check if it exists
                field.name // Return the resource name
            } catch (e: Exception) {
               throw IllegalArgumentException("No translation found for $field")
            }
        }
    }
}