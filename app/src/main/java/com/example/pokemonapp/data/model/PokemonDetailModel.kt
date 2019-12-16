package com.example.pokemonapp.data.model

data class PokemonDetailModel(
    var evolutions: List<Evolution>,
    var moves: List<Move>,
    var pokemon: Pokemon,
    var stats: StatsModel
)