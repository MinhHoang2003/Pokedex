package com.example.pokemonapp.data

import com.example.pokemonapp.data.model.Item
import com.example.pokemonapp.data.model.Items
import com.example.pokemonapp.data.remote.Service
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ItemRepository(private val service: Service) {

    suspend fun getItems(page : Int): Result<Items?> {
        return withContext(Dispatchers.IO) {
            val response = service.getItems(page)
            if (response.isSuccessful) {
                return@withContext Result.Succeess(response.body())
            } else return@withContext Result.Error(Exception(response.message()))
        }
    }

    suspend fun getItemByName(name: String): Result<Item?> {
        return withContext(Dispatchers.IO) {
            val response = service.getItem(name)
            if (response.isSuccessful) {
                return@withContext Result.Succeess(response.body())
            } else return@withContext Result.Error(Exception(response.message()))
        }
    }

    suspend fun searchItems(name: String): Result<Items?> {
        return withContext(Dispatchers.IO) {
            val response = service.searchItems(name)
            if (response.isSuccessful) {
                return@withContext Result.Succeess(response.body())
            } else return@withContext Result.Error(Exception(response.toString()))
        }
    }
}