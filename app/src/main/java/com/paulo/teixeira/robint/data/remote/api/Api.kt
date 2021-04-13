package com.paulo.teixeira.robint.data.remote.api

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.paulo.teixeira.robint.BuildConfig
import com.paulo.teixeira.robint.data.local.preference.PreferencesHelper
import com.paulo.teixeira.robint.data.model.user.User
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit


interface Api {
    
    
    class Factory {
        
        companion object {
            fun create(preferencesHelper: PreferencesHelper): Api {
                val okHttpClient: OkHttpClient.Builder =
                    OkHttpClient.Builder().connectTimeout(1, TimeUnit.MINUTES).readTimeout(1, TimeUnit.MINUTES)
                
                val interceptor = HttpLoggingInterceptor()
                interceptor.level = if(BuildConfig.DEBUG) {
                    HttpLoggingInterceptor.Level.BODY
                } else {
                    HttpLoggingInterceptor.Level.NONE
                }
                
                val requestInterceptor = RequestInterceptor(preferencesHelper)
                okHttpClient.addInterceptor(interceptor).addInterceptor(requestInterceptor).build()
                
                val retrofit = Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                    .addCallAdapterFactory(CoroutineCallAdapterFactory()).client(okHttpClient.build()).build()
                
                return retrofit.create(Api::class.java)
            }
        }
    }
    
    @FormUrlEncoded
    @Headers(ApiConstant.API_CONSTANTS)
    @POST(ApiConstant.FETCH_API_TOKEN)
    suspend fun doLogin(@Field("grant_type") grantType: String, @Field("Username") username: String,
        @Field("Password") password: String): User


}