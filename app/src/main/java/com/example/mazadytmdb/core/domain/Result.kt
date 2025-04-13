package com.example.mazadytmdb.core.domain

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(
        val exception: Throwable? = null,
        val message: String? = null,
        val code: Int? = null
    ) : Result<Nothing>()
}