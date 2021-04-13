package com.paulo.teixeira.robint.ui.forum

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val forumModule = module {
    factory<ForumContract.Repository> { ForumRepository(get(), get()) }
    viewModel { ForumViewModel(get(), get()) }
}