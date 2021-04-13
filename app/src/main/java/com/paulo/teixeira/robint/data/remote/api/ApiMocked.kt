package com.paulo.teixeira.robint.data.remote.api

import android.content.Context
import com.google.gson.Gson
import com.paulo.teixeira.robint.data.model.user.User
import java.io.InputStream
import java.lang.reflect.Type

@Suppress("BlockingMethodInNonBlockingContext")
open class ApiMocked(private val context: Context) : Api {

    private fun <C> getObjectFromFile(fileName: String, clazz: Class<C>): C {
        val inputStream: InputStream = context.assets.open(fileName)
        val inputString = inputStream.bufferedReader().use { it.readText() }

        return Gson().fromJson<C>(inputString, clazz)
    }

    private fun <C> getObjectFromFileListType(fileName: String, listTypeToken: Type): C {
        val inputStream: InputStream = context.assets.open(fileName)
        val inputString = inputStream.bufferedReader().use { it.readText() }

        return Gson().fromJson(inputString, listTypeToken)
    }

    override suspend fun doLogin(grantType: String, username: String, password: String): User {
        return getObjectFromFile("mockApi/get_login.json", User::class.java)
    }

}