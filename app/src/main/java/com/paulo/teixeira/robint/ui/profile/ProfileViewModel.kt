package com.paulo.teixeira.robint.ui.profile

import com.paulo.teixeira.robint.base.BaseViewModel
import com.paulo.teixeira.robint.helper.exception.ExceptionHelper


class ProfileViewModel(
    private val repository: ProfileContract.Repository,
    exception: ExceptionHelper
) : BaseViewModel(exception), ProfileContract.ViewModel {

    override fun clearPreferenceData() {}
}