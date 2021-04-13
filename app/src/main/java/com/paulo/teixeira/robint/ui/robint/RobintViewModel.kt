package com.paulo.teixeira.robint.ui.robint

import com.paulo.teixeira.robint.base.BaseViewModel
import com.paulo.teixeira.robint.helper.exception.ExceptionHelper


class RobintViewModel(
    private val repository: RobintContract.Repository,
    exception: ExceptionHelper
) : BaseViewModel(exception), RobintContract.ViewModel {

    override fun clearPreferenceData() {}
}