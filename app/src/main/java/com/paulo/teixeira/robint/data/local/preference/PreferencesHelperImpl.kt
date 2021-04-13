package com.paulo.teixeira.robint.data.local.preference

import android.content.Context
import androidx.preference.PreferenceManager
import com.google.gson.Gson
import com.paulo.teixeira.robint.data.model.user.User
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine

class PreferencesHelperImpl(private val context: Context) : PreferencesHelper {

    @ExperimentalCoroutinesApi
    override suspend fun clearAllData(): Boolean {
        return suspendCancellableCoroutine { continuation ->
            try {
                val isCleared = clearAll()

                if (isCleared) {
                    continuation.resume(true) {}
                } else {
                    continuation.cancel(Throwable())
                }
            } catch (exception: Exception) {
                exception.printStackTrace()
                continuation.cancel(exception)
            }
        }
    }

    //region Token
    override fun clearAuthorization() {
        putString(PreferencesHelper.AUTHENTICATION, null)
    }

    override fun saveAuthentication(user: User?) {
        user?.let {
            putString(PreferencesHelper.AUTHENTICATION, Gson().toJson(it))
        }
    }

    override fun getAuthentication(): User? {
        val json = getString(PreferencesHelper.AUTHENTICATION)

        json?.let {
            return Gson().fromJson(json, User::class.java)
        }
        return null
    }

    override fun clearAuthentication() {
        putString(PreferencesHelper.AUTHENTICATION, null)
    }

    //region Methods Preferences
    private fun putString(key: String, value: String?) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getString(key: String): String? {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPreferences.getString(key, null)
    }

    private fun putInt(key: String, value: Int) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = sharedPreferences.edit()
        editor.putInt(key, value)
        editor.apply()
    }

    private fun getInt(key: String): Int {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPreferences.getInt(key, 0)
    }

    private fun putLong(key: String, value: Long) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = sharedPreferences.edit()
        editor.putLong(key, value)
        editor.apply()
    }

    private fun getLong(key: String): Long {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPreferences.getLong(key, 0)
    }

    private fun putBoolean(key: String, value: Boolean) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = sharedPreferences.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    private fun getBoolean(key: String): Boolean {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPreferences.getBoolean(key, true)
    }

    private fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPreferences.getBoolean(key, defaultValue)
    }

    private fun remove(key: String) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        sharedPreferences.edit().clear().apply()
        return sharedPreferences.edit().remove(key).apply()
    }

    private fun clearAll(): Boolean {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPreferences.edit().clear().commit()
    }
    //endregion Methods Preferences
}