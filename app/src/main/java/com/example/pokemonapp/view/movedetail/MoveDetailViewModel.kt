package com.example.pokemonapp.view.movedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemonapp.data.MoveRepository
import com.example.pokemonapp.data.Result
import com.example.pokemonapp.data.model.Move
import com.example.pokemonapp.data.remote.RemoteClient
import com.example.pokemonapp.data.remote.Service
import kotlinx.coroutines.launch

class MoveDetailViewModel : ViewModel() {


    private val service: Service = RemoteClient.getClien().create(Service::class.java)
    val moveRepository = MoveRepository(service)

    private val _move = MutableLiveData<Move>()
    val move: LiveData<Move> = _move

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isException = MutableLiveData<String>()
    val isException: LiveData<String> = _isException

    fun getMove(name: String) = viewModelScope.launch {
        _isLoading.value = true
        val response = moveRepository.getMoveByName(name)
        if (response is Result.Succeess) {
            _isLoading.value = false
            _move.value = response.data
        } else {
            _isLoading.value = false
            _isException.value = response.toString()
        }
    }


}