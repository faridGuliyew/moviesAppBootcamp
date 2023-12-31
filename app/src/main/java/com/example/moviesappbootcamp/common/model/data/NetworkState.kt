package com.example.moviesappbootcamp.common.model.data

sealed class NetworkState<out T> {
    data class Success<out T> (val data : T?) : NetworkState<T>()
    data class Error (val errorMessage : String) : NetworkState<Nothing>()
}
