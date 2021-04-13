package com.paulo.teixeira.robint.ui.robint

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val robintModule = module {
    factory<RobintContract.Repository> { RobintRepository(get(), get()) }
    viewModel { RobintViewModel(get(), get()) }
}