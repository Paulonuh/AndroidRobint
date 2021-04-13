package com.paulo.teixeira.robint.base

interface BaseValidatorHelper {

    companion object {
        const val NO_ERROR = -1
    }

    fun validate(vararg anys: Any?): Int
}