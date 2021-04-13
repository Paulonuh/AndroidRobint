package com.paulo.teixeira.robint.ui.forum

import com.paulo.teixeira.robint.data.local.preference.PreferencesHelper
import com.paulo.teixeira.robint.data.remote.apihelper.ApiHelper

class ForumRepository(
    private val apiHelper: ApiHelper,
    private val preferencesHelper: PreferencesHelper
) : ForumContract.Repository {

}