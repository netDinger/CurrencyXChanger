package com.ppk.currencyxchanger.util

import com.google.gson.GsonBuilder
import retrofit2.Response
import java.io.IOException

object ResponseErrorHandler {
    fun <T : Any> handleResponseError(resp: Response<T>): AppResult.Failure {
        val error = parseError(resp)
        return AppResult.Failure(Exception(error.message))
    }


    private fun returnError(error: String? = "Something went wrong"): AppResult.Failure {
        return AppResult.Failure(Exception(error))
    }

    fun parseError(response: Response<*>): APIError {

        val gson = GsonBuilder().create()
        val error: APIError

        try {
            error = gson.fromJson(response.errorBody()?.string(), APIError::class.java)
        } catch (e: IOException) {
            e.message?.let {
                APIError(e.message.toString())
            }
            return APIError()
        }
        return error
    }

}

data class APIError(val message: String) {
    constructor() : this("")
}