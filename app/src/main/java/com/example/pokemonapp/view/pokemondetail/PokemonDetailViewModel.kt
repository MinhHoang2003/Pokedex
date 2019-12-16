package com.example.pokemonapp.view.pokemondetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemonapp.data.PokemonRepository
import com.example.pokemonapp.data.Result
import com.example.pokemonapp.data.model.PokemonDetailModel
import com.example.pokemonapp.data.remote.RemoteClient
import com.example.pokemonapp.data.remote.Service
import kotlinx.coroutines.launch

class PokemonDetailViewModel : ViewModel() {

    private val service: Service = RemoteClient.getClien().create(Service::class.java)
    val pokemonRepository = PokemonRepository(service)

    private val _pokemonDetail = MutableLiveData<PokemonDetailModel>()
    val pokemonDetail: LiveData<PokemonDetailModel> = _pokemonDetail

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isException = MutableLiveData<String>()
    val isException: LiveData<String> = _isException

    fun getPokemonDetail(id: String) = viewModelScope.launch {
        _isLoading.value = true
        val response = pokemonRepository.getPokemonById(id)
        if (response is Result.Succeess) {
            _isLoading.value = false
            _pokemonDetail.value = response.data
        } else {
            _isLoading.value = false
            _isException.value = response.toString()
        }
    }

}