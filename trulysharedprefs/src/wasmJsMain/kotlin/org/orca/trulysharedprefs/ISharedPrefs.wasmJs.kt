package org.orca.trulysharedprefs

import kotlinx.browser.window
import org.w3c.dom.get
import org.w3c.dom.set

/**
 * Factory for creating a [SharedPrefs] instance
 */
actual class SharedPrefsFactory {

    actual fun createSharedPrefs(): ISharedPrefs {
        return SharedPrefs(
            get = { key ->
                return@SharedPrefs window.localStorage[key]
            },
            _editSync = { block ->
                val editor = Editor()
                editor(block)

                return@SharedPrefs true
            },
            _editAsync = { block ->
                val editor = Editor()
                editor(block)
            }
        )
    }

    private class Editor : SharedPrefsEditor {

        override fun putString(key: String, value: String) {
            window.localStorage[key] = value
        }

        override fun putInt(key: String, value: Int) {
            window.localStorage[key] = value.toString()
        }

        override fun putBoolean(key: String, value: Boolean) {
            window.localStorage[key] = value.toString()
        }

        override fun remove(key: String) {
            window.localStorage.removeItem(key)
        }

        operator fun invoke(block: SharedPrefsEditor.() -> Unit) {
            block()
        }

    }
}


