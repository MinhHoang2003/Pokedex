package com.example.pokemonapp.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemonapp.data.ItemRepository
import com.example.pokemonapp.data.MoveRepository
import com.example.pokemonapp.data.PokemonRepository
import com.example.pokemonapp.data.Result
import com.example.pokemonapp.data.model.Items
import com.example.pokemonapp.data.model.Moves
import com.example.pokemonapp.data.model.PokemonDetailModel
import com.example.pokemonapp.data.model.Pokemons
import com.example.pokemonapp.data.remote.RemoteClient
import com.example.pokemonapp.data.remote.Service
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    val service = RemoteClient.getClien().create(Service::class.java)
    private val pokemonRepository = PokemonRepository(service)
    private val moveRepository = MoveRepository(service)
    private val itemRepository = ItemRepository(service)

    val query = MutableLiveData<String>()

    private val _pokemons = MutableLiveData<Pokemons>()
    val pokemons: LiveData<Pokemons> = _pokemons

    private val _pokemon = MutableLiveData<PokemonDetailModel>()
    val pokemon: LiveData<PokemonDetailModel> = _pokemon

    private val _moves = MutableLiveData<Moves>()
    val moves: LiveData<Moves> = _moves

    private val _items = MutableLiveData<Items>()
    val items: LiveData<Items> = _items

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isException = MutableLiveData<String>()
    val isExeption: LiveData<String> = _isException

    fun getPokemons() = viewModelScope.launch {
        _isLoading.value = true
        val result = pokemonRepository.getPokemons()
        if (result is Result.Succeess) {
            _isLoading.value = false;
            _pokemons.value = result.data
        } else {
            _isLoading.value = false;
            _isException.value = result.toString()
        }
    }

    fun getMoves() = viewModelScope.launch {
        _isLoading.value = true
        val result = moveRepository.getMoves()
        if (result is Result.Succeess) {
            _isLoading.value = false
            _moves.value = result.data
        } else {
            _isLoading.value = false
            _isException.value = result.toString()
        }
    }

    fun getItems() = viewModelScope.launch {
        _isLoading.value = true
        val result = itemRepository.getItems()
        if (result is Result.Succeess) {
            _isLoading.value = false
            _items.value = result.data
        } else {
            _isLoading.value = false
            _isException.value = result.toString()
        }
    }

    fun getPokemon(id: String) = viewModelScope.launch {
        val result = pokemonRepository.getPokemonById(id)
        if (result is Result.Succeess) {
            _pokemon.value = result.data
        } else {
            _isException.value = result.toString()
        }
    }

}