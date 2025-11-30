package com.photography.data.remote.client

import android.util.Log
import com.photography.utils.errorObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

fun <T> toResultFlow(call: suspend () -> Response<T>): Flow<Result<T>> = flow {
    emit(Result.Loading)
    val response = runCatching { call.invoke() }
    if (response.isSuccess && response.getOrNull()?.isSuccessful == true) {
        val body = response.getOrNull()?.body()
        Log.d("toResultFlow", "response: $body")
        if (body != null) {
            emit(Result.Success(data = body))
        } else {
            emit(Result.Error(error = Throwable("Response body is null")))
        }
    } else {
        val error = response.getOrNull()?.errorBody()?.errorObject<ErrorBody>()
        val errorMessage = error?.errors?.firstOrNull() ?: "Unknown error occurred"
        Log.e("toResultFlow", "ResponseError: $errorMessage")
        emit(Result.Error(error = Throwable(errorMessage)))
    }

}