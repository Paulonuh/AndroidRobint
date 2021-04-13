package com.paulo.teixeira.robint.ui.forum

import com.paulo.teixeira.robint.base.BaseViewModel
import com.paulo.teixeira.robint.helper.exception.ExceptionHelper


class ForumViewModel(
    private val repository: ForumContract.Repository,
    exception: ExceptionHelper
) : BaseViewModel(exception), ForumContract.ViewModel {

    override fun clearPreferenceData() {}
}