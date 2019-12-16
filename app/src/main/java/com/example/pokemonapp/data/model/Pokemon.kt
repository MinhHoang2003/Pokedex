package com.example.pokemonapp.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class Pokemon(
    var description: String,
    var id: String,
    var image: String,
    var name: String,
    @SerializedName("pokemonTypes")
    @Expose
    var pokemonTypes: List<String>
)