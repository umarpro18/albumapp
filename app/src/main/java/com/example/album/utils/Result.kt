package com.example.album.utils

/**
 * Sealed call responsible to hold api response data based on stated.
 */

sealed class Result<out T : Any> {

    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Error(val exception: Exception, val statusCode: Int? = null) : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
        }
    }
}
