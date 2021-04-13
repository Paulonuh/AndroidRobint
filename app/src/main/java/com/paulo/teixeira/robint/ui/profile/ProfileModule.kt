package com.paulo.teixeira.robint.ui.profile

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val profileModule = module {
    factory<ProfileContract.Repository> { ProfileRepository(get(), get()) }
    viewModel { ProfileViewModel(get(), get()) }
}