package com.example.pokemonapp.data.remote

import com.example.pokemonapp.data.model.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface Service {

    @GET("api/v1/items")
    suspend fun getItems(): Response<Items>

    @GET("api/v1/items/{name}")
    suspend fun getItem(@Path("name") name: String): Response<Item>

    @GET("api/v1/moves")
    suspend fun getMoves(): Response<Moves>

    @GET("api/v1/moves/{name}")
    suspend fun getMove(@Path("name") name: String): Response<Move>

    @GET("api/v1/pokemons")
    suspend fun getPokemons(): Response<Pokemons>

    @GET("api/v1/pokemons/{id}")
    suspend fun getPokemon(@Path("id") id: String): Response<PokemonDetailModel>

    @GET("api/v1/weaknesses/{pokemonType}")
    suspend fun getWeaknesses(): Response<List<Weakness>>

}