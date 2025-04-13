package com.example.mazadytmdb.core.domain

sealed class ApiError {
    data class NetworkError(val cause: Throwable) : ApiError()
    data class ServerError(val code: Int, val message: String?) : ApiError()
    data object NoInternetError : ApiError()
}