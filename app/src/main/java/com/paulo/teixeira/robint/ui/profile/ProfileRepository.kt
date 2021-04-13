package com.paulo.teixeira.robint.ui.profile

import com.paulo.teixeira.robint.data.local.preference.PreferencesHelper
import com.paulo.teixeira.robint.data.remote.apihelper.ApiHelper

class ProfileRepository(
    private val apiHelper: ApiHelper,
    private val preferencesHelper: PreferencesHelper
) : ProfileContract.Repository {

}