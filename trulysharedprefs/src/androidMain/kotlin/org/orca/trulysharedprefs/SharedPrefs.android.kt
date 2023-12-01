package org.orca.trulysharedprefs

import android.content.SharedPreferences

/**
 * Factory for creating a [SharedPrefs] instance.
 *
 * **Example:**
 * ```kotlin
 * val sharedPreferences = getSharedPreferences("prefs", MODE_PRIVATE)
 * val prefs: ISharedPrefs = SharedPrefsFactory(sharedPreferences).createSharedPrefs()
 *
 * // Use SharedPrefs here ...
 * ```
 */
actual class SharedPrefsFactory(private val sharedPreferences: SharedPreferences) {
    actual fun createSharedPrefs(): ISharedPrefs {

        return SharedPrefs(
            get = { key ->
                return@SharedPrefs sharedPreferences.getString(key, null)
            },
            _editSync = { block ->
                val sharedPreferencesEditor = sharedPreferences.edit()
                val editor = Editor(sharedPreferencesEditor)

                editor(block)

                return@SharedPrefs sharedPreferencesEditor.commit()
            },
            _editAsync = { block ->
                val sharedPreferencesEditor = sharedPreferences.edit()
                val editor = Editor(sharedPreferencesEditor)

                editor(block)

                return@SharedPrefs sharedPreferencesEditor.apply()
            }
        )
    }

    private class Editor(val editor: SharedPreferences.Editor) : SharedPrefsEditor {

        override fun putString(key: String, value: String) {
            editor.putString(key, value)
        }

        override fun putInt(key: String, value: Int) {
            editor.putString(key, value.toString())
        }

        override fun putBoolean(key: String, value: Boolean) {
            editor.putString(key, value.toString())
        }

        override fun remove(key: String) {
            editor.remove(key)
        }

        operator fun invoke(block: SharedPrefsEditor.() -> Unit) {
            block()
        }
    }
}