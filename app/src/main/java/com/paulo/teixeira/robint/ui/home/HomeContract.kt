package com.paulo.teixeira.robint.ui.home

import androidx.lifecycle.LiveData
import com.paulo.teixeira.robint.base.BaseContract

interface HomeContract {

    interface ViewModel : BaseContract.ViewModel {
        val isLoggedOut: LiveData<Boolean>

        fun logout()
    }

    interface Repository {
        suspend fun clearAllData()
    }
}