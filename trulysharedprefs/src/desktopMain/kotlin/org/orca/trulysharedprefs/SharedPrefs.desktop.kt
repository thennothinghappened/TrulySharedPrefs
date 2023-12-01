package org.orca.trulysharedprefs

import java.util.prefs.Preferences

/**
 * Factory for creating a [SharedPrefs] instance.
 *
 * **Example:**
 * ```kotlin
 * val preferences = Preferences.userNodeForPackage(MainKt::class.java)
 * val prefs: ISharedPrefs = SharedPrefsFactory(preferences).createSharedPrefs()
 *
 * // Use SharedPrefs here ...
 * ```
 */
actual class SharedPrefsFactory(private val preferences: Preferences) {

    /**
     * Factory for creating a [SharedPrefs] instance, automatically getting
     * the preference node for using with the JVM's [java.util.prefs.Preferences].
     *
     * **Example:**
     * ```kotlin
     * val prefs: ISharedPrefs = SharedPrefsFactory(MainKt::class.java)
     *     .createSharedPrefs()
     *
     * // Use SharedPrefs here ...
     * ```
     */
    constructor(clazz: Class<*>) : this(
        Preferences.userNodeForPackage(clazz)
    )

    actual fun createSharedPrefs(): ISharedPrefs {
        return SharedPrefs(
            get = { key ->
                return@SharedPrefs preferences.get(key, null)
            },
            _editSync = { block ->
                val editor = Editor(preferences)
                editor(block)

                return@SharedPrefs true
            },
            _editAsync = { block ->
                val editor = Editor(preferences)
                editor(block)
            }
        )
    }

    private class Editor(val preferences: Preferences) : SharedPrefsEditor {
        override fun putString(key: String, value: String) {
            preferences.put(key, value)
        }

        override fun putInt(key: String, value: Int) {
            preferences.putInt(key, value)
        }

        override fun putBoolean(key: String, value: Boolean) {
            preferences.putBoolean(key, value)
        }

        override fun remove(key: String) {
            preferences.remove(key)
        }

        operator fun invoke(block: SharedPrefsEditor.() -> Unit) {
            block()
        }
    }
}