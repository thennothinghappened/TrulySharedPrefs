package org.orca.trulysharedprefs

import android.annotation.SuppressLint
import android.content.SharedPreferences

actual class SharedPrefsFactory(private val sharedPreferences: SharedPreferences) {
    actual fun createSharedPrefs(): SharedPrefs {
        return AndroidSharedPrefs(sharedPreferences)
    }
}

class AndroidSharedPrefs(private val sharedPreferences: SharedPreferences) : SharedPrefs {

    private class Editor(val editor: SharedPreferences.Editor) : SharedPrefsEditor {
        override fun putString(key: String, value: String?) {
            if (value == null) {
                editor.remove(key)
                return
            }

            editor.putString(key, value)
        }

        override fun putInt(key: String, value: Int?) {
            if (value == null) {
                editor.remove(key)
                return
            }

            editor.putInt(key, value)
        }

        override fun putBoolean(key: String, value: Boolean?) {
            if (value == null) {
                editor.remove(key)
                return
            }

            editor.putBoolean(key, value)
        }

        operator fun invoke(block: SharedPrefsEditor.() -> Unit) {
            block()
        }
    }

    override fun <T : String?> getString(key: String, default: T): T {
        return sharedPreferences.getString(key, default) as T
    }

    override fun getInt(key: String, default: Int): Int {
        return sharedPreferences.getInt(key, default)
    }

    override fun getBoolean(key: String, default: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, default)
    }

    @SuppressLint("ApplySharedPref")
    override fun editSync(block: SharedPrefsEditor.() -> Unit): Boolean {
        val sharedPreferencesEditor = sharedPreferences.edit()
        val editor = Editor(sharedPreferencesEditor)

        editor(block)

        return sharedPreferencesEditor.commit()
    }

    override suspend fun editAsync(block: SharedPrefsEditor.() -> Unit) {
        val sharedPreferencesEditor = sharedPreferences.edit()
        val editor = Editor(sharedPreferencesEditor)

        editor(block)

        return sharedPreferencesEditor.apply()
    }

}