package com.paulo.teixeira.robint.ui.robint

import com.paulo.teixeira.robint.data.local.preference.PreferencesHelper
import com.paulo.teixeira.robint.data.remote.apihelper.ApiHelper

class RobintRepository(
    private val apiHelper: ApiHelper,
    private val preferencesHelper: PreferencesHelper
) : RobintContract.Repository {

}