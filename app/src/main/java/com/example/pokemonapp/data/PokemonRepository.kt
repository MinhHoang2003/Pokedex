package com.example.pokemonapp.data

import com.example.pokemonapp.data.model.PokemonDetailModel
import com.example.pokemonapp.data.model.Pokemons
import com.example.pokemonapp.data.remote.Service
import com.example.pokemonapp.getThreadName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PokemonRepository(val service: Service) {

    suspend fun getPokemons(page: Int): Result<Pokemons?> {
        return withContext(Dispatchers.IO) {
            getThreadName("getPokemons")
            val response = service.getPokemons(page)
            if (response.isSuccessful) {
                return@withContext Result.Succeess(response.body())
            } else return@withContext Result.Error(Exception(response.message()))
        }
    }

    suspend fun getPokemonById(id: String): Result<PokemonDetailModel?> {
        return withContext(Dispatchers.IO) {
            val response = service.getPokemon(id)
            if (response.isSuccessful) {
                return@withContext Result.Succeess(response.body())
            } else return@withContext Result.Error(Exception(response.message()))
        }
    }
}