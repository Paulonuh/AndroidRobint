package com.paulo.teixeira.robint.ui.home

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {
    factory<HomeContract.Repository> { HomeRepository(get()) }
    viewModel { HomeViewModel(get(), get()) }
    viewModel { SharedViewModel() }

}