package com.paulo.teixeira.robint.data.remote.api

import com.paulo.teixeira.robint.data.local.preference.PreferencesHelper
import okhttp3.Interceptor
import okhttp3.Response
import okio.IOException


class RequestInterceptor(private val preferencesHelper: PreferencesHelper) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        requestBuilder.addHeader("Content-Type", "application/json")

        val authorization = "TOKEN"

        authorization?.let {
            requestBuilder.addHeader("Authorization", "Bearer $authorization")
        }

        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}