package com.paulo.teixeira.robint.ui.home

import androidx.lifecycle.ViewModel
import com.paulo.teixeira.robint.util.SingleLiveData

class SharedViewModel : ViewModel() {

    var toLogout = SingleLiveData<Boolean>()
    var toOpenMenu = SingleLiveData<Boolean>()
    var openOption = SingleLiveData<Int>()

}