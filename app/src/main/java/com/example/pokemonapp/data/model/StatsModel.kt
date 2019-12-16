package com.example.pokemonapp.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class StatsModel(
    @SerializedName("basicStatses")
    @Expose
    var basicStatses: List<BasicStats>,
    var breeding: Breeding,
    @SerializedName("weaknesses")
    @Expose
    var weaknesses: List<Weakness>
)