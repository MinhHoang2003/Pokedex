package com.example.pokemonapp.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Weakness(
    @SerializedName("effect")
    @Expose
    var effect: Float,
    @SerializedName("pokemonType")
    @Expose
    var name: String
)