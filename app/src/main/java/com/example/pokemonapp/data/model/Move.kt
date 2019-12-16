package com.example.pokemonapp.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class Move(
    @SerializedName("accuracy")
    @Expose
    var accuracy: Int,
    @SerializedName("effects")
    @Expose
    var effects: String,
    @SerializedName("name")
    @Expose
    var name: String,
    @SerializedName("power")
    @Expose
    var power: Int,
    @SerializedName("pp")
    var pp: Int,
    @SerializedName("type")
    @Expose
    var type: String
)