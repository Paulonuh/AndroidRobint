package com.paulo.teixeira.robint.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.paulo.teixeira.robint.base.BaseViewModel
import com.paulo.teixeira.robint.helper.exception.ExceptionHelper

class HomeViewModel(
    private val repository: HomeContract.Repository,
    exception: ExceptionHelper
) : BaseViewModel(exception), HomeContract.ViewModel {

    private val mLogout = MutableLiveData<Boolean>()

    override val isLoggedOut: LiveData<Boolean>
        get() = mLogout

    override fun logout() {
        defaultLaunch {
            repository.clearAllData()
            mLogout.postValue(true)
        }
    }

    override fun clearPreferenceData() {
        defaultLaunch {
            repository.clearAllData()
        }
    }
}