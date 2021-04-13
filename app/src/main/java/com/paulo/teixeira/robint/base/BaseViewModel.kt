package com.paulo.teixeira.robint.base

import androidx.annotation.IdRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paulo.teixeira.robint.R
import com.paulo.teixeira.robint.helper.exception.ExceptionHelper
import com.paulo.teixeira.robint.util.SingleLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection

abstract class BaseViewModel(private val exception: ExceptionHelper) : ViewModel(),
    BaseContract.ViewModel {

    override val loading: LiveData<Boolean>
        get() = mLoading

    override val message: LiveData<Any>
        get() = mMessage

    val redirect: LiveData<Int>
        get() = mRedirect

    private val mMessage = SingleLiveData<Any>()
    private val mLoading = SingleLiveData<Boolean>()
    private val mRedirect = SingleLiveData<@IdRes Int>()

    protected fun defaultLaunch(
        auxLoading: MutableLiveData<Boolean>? = null, validatorHelper: BaseValidatorHelper? = null,
        vararg any: Any?, block: suspend CoroutineScope.() -> Unit
    ) {
        viewModelScope.launch {
            try {
                mLoading.postValue(true)
                auxLoading?.postValue(true)

                validatorHelper?.let {
                    val validation = validatorHelper.validate(*any)
                    if (validation != BaseValidatorHelper.NO_ERROR) {
                        mLoading.postValue(false)
                        auxLoading?.postValue(false)
                        mMessage.postValue(validation)
                        return@launch
                    }
                }

                block.invoke(this)
            } catch (exception: Exception) {
                doAtMainThread { handleException(exception) }
            } finally {
                mLoading.postValue(false)
                auxLoading?.postValue(false)
            }
        }
    }

    private suspend fun doAtMainThread(doFunction: () -> Unit) {
        withContext(Dispatchers.Main) {
            doFunction.invoke()
        }
    }

    private fun handleException(exception: Exception) {
        val error = this.exception.message(exception, true)
        when (error.code) {
            HttpURLConnection.HTTP_UNAUTHORIZED -> {
                mRedirect.postValue(HttpURLConnection.HTTP_UNAUTHORIZED)
                mMessage.postValue(error.message)
                clearPreferenceData()
            }
            HttpURLConnection.HTTP_INTERNAL_ERROR -> {
                mMessage.postValue(R.string.failed_server)
            }
            else -> {
                mMessage.postValue(error.message)
            }
        }
    }

    abstract fun clearPreferenceData()
}