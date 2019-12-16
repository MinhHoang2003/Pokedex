package com.example.pokemonapp.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteClient {
    companion object {
        private const val BASE_URL = "http://app11.lifetimetech.vn/pokedex/"
        var CLIENT: Retrofit? = null
        fun getClien(): Retrofit = CLIENT ?: synchronized(this) {
            return buildClient()
        }

        private fun buildClient(): Retrofit {
            return Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}