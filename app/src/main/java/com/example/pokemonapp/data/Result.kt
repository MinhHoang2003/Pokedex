package com.example.pokemonapp.data

sealed class Result<out R> {

    data class Succeess<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
    object Loading : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Succeess<*> -> "Success[data = $data]"
            is Error -> "Error[exception = $exception]"
            Loading -> "Loading"
        }
    }
}
val Result<*>.succeeded
    get() = this is Result.Succeess && data != null