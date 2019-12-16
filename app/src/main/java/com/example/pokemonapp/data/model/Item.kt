package com.example.pokemonapp.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Item(
    @SerializedName("effects")
    var effects: String,
    @SerializedName("image")
    @Expose
    var image: String,
    @SerializedName("name")
    @Expose
    var name: String,
    @SerializedName("price")
    @Expose
    var price: Int
)