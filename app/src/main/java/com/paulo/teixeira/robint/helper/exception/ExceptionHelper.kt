package com.paulo.teixeira.robint.helper.exception

interface ExceptionHelper {
    
    fun message(exception: Throwable, readApiMessage: Boolean? = true, defaultMessageRes: Int? = null): ErrorMessage
}