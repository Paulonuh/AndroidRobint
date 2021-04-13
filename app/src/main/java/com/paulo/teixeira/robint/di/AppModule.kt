package com.paulo.teixeira.robint.di

import com.paulo.teixeira.robint.BuildConfig
import com.paulo.teixeira.robint.data.local.preference.PreferencesHelper
import com.paulo.teixeira.robint.data.local.preference.PreferencesHelperImpl
import com.paulo.teixeira.robint.data.remote.api.Api
import com.paulo.teixeira.robint.data.remote.api.ApiMocked
import com.paulo.teixeira.robint.data.remote.apihelper.ApiHelper
import com.paulo.teixeira.robint.data.remote.apihelper.ApiHelperImpl
import com.paulo.teixeira.robint.helper.exception.ExceptionHelper
import com.paulo.teixeira.robint.helper.exception.ExceptionHelperImpl
import org.koin.dsl.module

val appModule = module {
    single<PreferencesHelper> { PreferencesHelperImpl(get()) }
    single<ApiHelper> { ApiHelperImpl(get()) }
    single<ExceptionHelper> { ExceptionHelperImpl(get()) }
    if(BuildConfig.MOCK)
    //mocked
        single { ApiMocked(get()) as Api}
    else
    //prod
        single { Api.Factory.create(get())}
}