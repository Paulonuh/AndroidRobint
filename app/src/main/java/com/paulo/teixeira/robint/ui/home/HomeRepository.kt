package com.paulo.teixeira.robint.ui.home

import com.paulo.teixeira.robint.data.local.preference.PreferencesHelper


class HomeRepository(
    private val preferencesHelper: PreferencesHelper
) : HomeContract.Repository {


    override suspend fun clearAllData(){
        preferencesHelper.clearAllData()
    }

}