package org.orca.trulysharedprefs

import kotlin.reflect.KProperty

/**
 * Factory for creating a [SharedPrefs] instance
 */
expect class SharedPrefsFactory {
    fun createSharedPrefs(): ISharedPrefs
}

/**
 * SharedPrefs instance, allows you to read key-value pairs,
 * and save new values with `editAsync` or `editSync`.
 *
 * **Example:**
 * ```kotlin
 * var name = prefs.getStringOrNull("name")
 *
 * while (name == null) {
 *     println("Enter your name:")
 *     name = readLine()
 * }
 *
 * prefs.editSync {
 *     putString("name", name)
 * }
 *
 * println("Hello, $name!")
 * ```
 */
interface ISharedPrefs {
    /**
     * Get a string value or return the default.
     */
    fun getString(key: String, default: String): String

    /**
     * Get a string value or return null.
     */
    fun getStringOrNull(key: String): String?

    /**
     * Get an Int value or return the default.
     */
    fun getInt(key: String, default: Int): Int

    /**
     * Get an Int value or return null.
     */
    fun getIntOrNull(key: String): Int?

    /**
     * Get a Boolean value or return the default.
     */
    fun getBoolean(key: String, default: Boolean): Boolean

    /**
     * Get a Boolean value or return null.
     */
    fun getBooleanOrNull(key: String): Boolean?

    /**
     * Whether the key exists.
     */
    operator fun contains(key: String): Boolean

    /**
     * Edit the preferences, saving synchronously.
     */
    fun editSync(block: SharedPrefsEditor.() -> Unit): Boolean

    /**
     * Edit the preferences, saving asynchronously.
     */
    suspend fun editAsync(block: SharedPrefsEditor.() -> Unit)
}

interface SharedPrefsEditor {
    /**
     * Set a String value.
     */
    fun putString(key: String, value: String)

    /**
     * Set an Int value
     */
    fun putInt(key: String, value: Int)

    /**
     * Set a Boolean value
     */
    fun putBoolean(key: String, value: Boolean)

    /**
     * Remove a key
     */
    fun remove(key: String)
}

internal class SharedPrefs(
    private val _editSync: (block: SharedPrefsEditor.() -> Unit) -> Boolean,
    private val _editAsync: suspend (block: SharedPrefsEditor.() -> Unit) -> Unit,
    private val get: (key: String) -> String?
) : ISharedPrefs {

    override fun getStringOrNull(key: String): String? {
        return get(key)
    }

    override fun getString(key: String, default: String): String {
        return getStringOrNull(key) ?: default
    }

    override fun getInt(key: String, default: Int): Int {
        return getIntOrNull(key) ?: default
    }

    override fun getIntOrNull(key: String): Int? {
        return getStringOrNull(key)?.toIntOrNull()
    }

    override fun getBoolean(key: String, default: Boolean): Boolean {
        return getBooleanOrNull(key) ?: default
    }

    override fun getBooleanOrNull(key: String): Boolean? {
        return getStringOrNull(key)?.toBooleanStrictOrNull()
    }

    override fun contains(key: String): Boolean {
        return get(key) != null
    }

    override fun editSync(block: SharedPrefsEditor.() -> Unit): Boolean {
        return _editSync(block)
    }

    override suspend fun editAsync(block: SharedPrefsEditor.() -> Unit) {
        return _editAsync(block)
    }
}