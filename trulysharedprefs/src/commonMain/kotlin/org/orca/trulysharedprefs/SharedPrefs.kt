package org.orca.trulysharedprefs

expect class SharedPrefsFactory {
    fun createSharedPrefs(): SharedPrefs
}

interface SharedPrefs {
    fun <T : String?> getString(key: String, default: T): T
    fun getInt(key: String, default: Int): Int
    fun getBoolean(key: String, default: Boolean): Boolean
    fun editSync(block: SharedPrefsEditor.() -> Unit): Boolean
    suspend fun editAsync(block: SharedPrefsEditor.() -> Unit)
}

interface SharedPrefsEditor {
    fun putString(key: String, value: String?)
    fun putInt(key: String, value: Int?)
    fun putBoolean(key: String, value: Boolean?)
}