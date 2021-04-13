package com.paulo.teixeira.robint.data.local.preference

import com.paulo.teixeira.robint.data.model.user.User


interface PreferencesHelper {

    companion object {
        const val AUTHENTICATION = "authentication"

    }

    suspend fun clearAllData(): Boolean

    fun clearAuthorization()
    fun saveAuthentication(user: User?)
    fun getAuthentication(): User?
    fun clearAuthentication()
}