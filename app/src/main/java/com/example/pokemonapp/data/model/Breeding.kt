package com.example.pokemonapp.data.model

import com.google.gson.annotations.SerializedName

data class Breeding(
    @SerializedName("eggGroup")
    var eggGroup: List<String>,
    var femaleGender: Float,
    var hatchTime: HatchTime
)