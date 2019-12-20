package com.example.pokemonapp.data.remote

import com.example.pokemonapp.data.model.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Service {

    @GET("api/v1/items")
    suspend fun getItems(@Query("page") page: Int? = 1): Response<Items>

    @GET("api/v1/items")
    suspend fun searchItems(@Query("name") name: String): Response<Items>

    @GET("api/v1/items/{name}")
    suspend fun getItem(@Path("name") name: String): Response<Item>


    @GET("api/v1/moves")
    suspend fun getMoves(@Query("page") page: Int? = 1): Response<Moves>

    @GET("api/v1/moves")
    suspend fun searchMove(@Query("name") name: String): Response<Moves>

    @GET("api/v1/moves/{name}")
    suspend fun getMove(@Path("name") name: String): Response<Move>

    @GET("api/v1/pokemons")
    suspend fun getPokemons(@Query("page") page: Int? = 1): Response<Pokemons>

    @GET("api/v1/pokemons")
    suspend fun searchPokemon(@Query("name") name: String): Response<Pokemons>

    @GET("api/v1/pokemons/{id}")
    suspend fun getPokemon(@Path("id") id: String): Response<PokemonDetailModel>

    @GET("api/v1/weaknesses/{pokemonType}")
    suspend fun getWeaknesses(): Response<List<Weakness>>

}