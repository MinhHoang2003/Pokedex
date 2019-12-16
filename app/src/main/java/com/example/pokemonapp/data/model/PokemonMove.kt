package com.example.pokemonapp.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PokemonMove(
    var level: Int,
    var name: String,
    @SerializedName("type")
    @Expose
    var type: List<String>
)