package com.example.pokemonapp.data

import com.example.pokemonapp.data.model.Move
import com.example.pokemonapp.data.model.Moves
import com.example.pokemonapp.data.remote.Service
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MoveRepository(private val service: Service) {

    suspend fun getMoves(page: Int?): Result<Moves?> {
        return withContext(Dispatchers.IO) {
            val response = service.getMoves(page)
            if (response.isSuccessful) {
                return@withContext Result.Succeess(response.body())
            } else return@withContext Result.Error(Exception(response.message()))
        }
    }

    suspend fun getMoveByName(name: String): Result<Move?> {
        return withContext(Dispatchers.IO) {
            val response = service.getMove(name)
            if (response.isSuccessful) {
                return@withContext Result.Succeess(response.body())
            } else return@withContext Result.Error(Exception(response.message()))
        }
    }

}