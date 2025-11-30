package com.photography.data.remote.client

//class ResponseEvent<out T>(
//    val status: Status,
//    val data: T?,
//    val message: String?
//) {
//    companion object {
//
//        fun <T> success(data: T?): ResponseEvent<T> {
//            return ResponseEvent(Status.SUCCESS, data, null)
//        }
//
//        fun <T> error(msg: String, data: T?): ResponseEvent<T> {
//            return ResponseEvent(Status.ERROR, data, msg)
//        }
//
//        fun <T> loading(data:T?): ResponseEvent<T>{
//            return ResponseEvent(Status.LOADING, data, null)
//        }
//
//    }
//}
//
//enum class Status {
//    SUCCESS,
//    ERROR,
//    LOADING
//}

sealed class ResponseEvent {
    data class Success(val data: Any): ResponseEvent()
    data class Failure(val error: Throwable): ResponseEvent()
    data object Loading : ResponseEvent()
}

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val error: Throwable) : Result<Nothing>()
    data object Loading : Result<Nothing>()
}