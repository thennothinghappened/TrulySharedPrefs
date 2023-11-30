package org.orca.trulysharedprefs

import java.util.prefs.Preferences

actual class SharedPrefsFactory(private val preferences: Preferences) {
    actual fun createSharedPrefs(): SharedPrefs {
        return DesktopSharedPrefs(preferences)
    }
}

class DesktopSharedPrefs(private val preferences: Preferences) : SharedPrefs {

    private val editor = object : SharedPrefsEditor {
        override fun putString(key: String, value: String?) {
            if (value == null) {
                return preferences.remove(key)
            }

            preferences.put(key, value)
        }

        override fun putInt(key: String, value: Int?) {
            if (value == null) {
                return preferences.remove(key)
            }

            preferences.putInt(key, value)
        }

        override fun putBoolean(key: String, value: Boolean?) {
            if (value == null) {
                return preferences.remove(key)
            }

            preferences.putBoolean(key, value)
        }

        operator fun invoke(block: SharedPrefsEditor.() -> Unit) {
            block()
        }
    }

    override fun <T : String?> getString(key: String, default: T): T {
        return preferences.get(key, default) as T
    }

    override fun getInt(key: String, default: Int): Int {
        return preferences.getInt(key, default)
    }

    override fun getBoolean(key: String, default: Boolean): Boolean {
        return preferences.getBoolean(key, default)
    }

    override fun editSync(block: SharedPrefsEditor.() -> Unit): Boolean {
        editor(block)
        return true
    }

    override suspend fun editAsync(block: SharedPrefsEditor.() -> Unit) {
        editSync(block)
    }

}