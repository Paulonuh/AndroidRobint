package com.paulo.teixeira.robint.helper.exception

import android.content.Context
import com.paulo.teixeira.robint.R
import org.json.JSONObject
import retrofit2.HttpException
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class ExceptionHelperImpl(private val context: Context) : ExceptionHelper {
    
    override fun message(
            exception: Throwable, readApiMessage: Boolean?, defaultMessageRes: Int?
                        ): ErrorMessage {
        val errorMessage = when (exception) {
            is HttpException -> httpErrorMessage(exception, readApiMessage)
            is UnknownHostException -> ErrorMessage(
                    context.getString(R.string.no_internet_verify_signal)
                                                   )
            is SocketTimeoutException -> ErrorMessage(
                    context.getString(R.string.no_server_response)
                                                     )
            else -> null
        }
        
        errorMessage?.let {
            return it
        }
        
        val message = defaultMessageRes ?: R.string.there_was_fail_try_again
        return ErrorMessage(
                context.getString(message)
                           )
    }
    
    private fun httpErrorMessage(exception: HttpException, readApiMessage: Boolean?): ErrorMessage? {
        if (readApiMessage == true) {
            try {
                exception.response()?.errorBody()?.let {
                    val error = JSONObject(
                            convertStreamToString(
                                    it.byteStream()
                                                 )
                                          )
                    return when {
                        error.has("validationErros") -> {
                            if (error.getJSONObject("validationErros").has("email")) {
                                ErrorMessage(
                                        error.getJSONObject("validationErros").getString("email")
                                            )
                            } else {
                                ErrorMessage(
                                        error.getString("validationErros")
                                            )
                            }
                        }
                        
                        else -> ErrorMessage(
                                error.getString("message"), error.getString("statusCode").toIntOrNull() ?: -1
                                            )
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        
        when {
            exception.code() == HttpURLConnection.HTTP_UNAUTHORIZED -> {
                return ErrorMessage(
                        context.getString(R.string.no_data), exception.code()
                                   )
            }
            exception.code() == HttpURLConnection.HTTP_NOT_FOUND -> {
                return ErrorMessage(
                        context.getString(R.string.no_data), exception.code()
                                   )
            }
            exception.code() == HttpURLConnection.HTTP_INTERNAL_ERROR -> {
                return ErrorMessage(
                        context.getString(R.string.failed_server), exception.code()
                                   )
            }
            else -> return null
        }
    }
    
    private fun convertStreamToString(inputStream: InputStream): String {
        val reader = BufferedReader(InputStreamReader(inputStream))
        var stringBuilder = ""
        
        try {
            var line: String? = null
            while ({ line = reader.readLine(); line }() != null) {
                line?.let {
                    stringBuilder += line + "\n"
                }
            }
        } catch (error: OutOfMemoryError) {
            error.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                inputStream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            
        }
        return stringBuilder
    }
}