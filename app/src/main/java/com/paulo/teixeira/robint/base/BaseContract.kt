package com.paulo.teixeira.robint.base

import androidx.lifecycle.LiveData
import org.koin.core.module.Module

interface BaseContract {
    
    interface ViewModel {
        val message: LiveData<Any>
        val loading: LiveData<Boolean>
    }
    
    interface View {
        val module: Module?
        val viewModel: BaseViewModel?
        
        fun onInitView()
        fun onInitObservers()
        fun onFetchInitialData()
        fun onError(message: Any)
        fun onLoading(isLoading: Boolean)
    }
}