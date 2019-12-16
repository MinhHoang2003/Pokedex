package com.example.pokemonapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Type(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo(name = "Name")
    var name: String,
    @ColumnInfo(name = "Image")
    var image: String,
    @ColumnInfo(name = "Description")
    var description: String
)