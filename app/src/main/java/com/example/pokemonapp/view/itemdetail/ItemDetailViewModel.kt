package com.example.pokemonapp.view.itemdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemonapp.data.ItemRepository
import com.example.pokemonapp.data.Result
import com.example.pokemonapp.data.model.Item
import com.example.pokemonapp.data.remote.RemoteClient
import com.example.pokemonapp.data.remote.Service
import kotlinx.coroutines.launch

class ItemDetailViewModel : ViewModel() {

    private val service: Service = RemoteClient.getClien().create(Service::class.java)
    val itemRepository = ItemRepository(service)

    private val _item = MutableLiveData<Item>()
    val item: LiveData<Item> = _item

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isException = MutableLiveData<String>()
    val isException: LiveData<String> = _isException

    fun getItem(name: String) = viewModelScope.launch {
        _isLoading.value = true
        val response = itemRepository.getItemByName(name)
        if (response is Result.Succeess) {
            _isLoading.value = false
            _item.value = response.data
        } else {
            _isLoading.value = false
            _isException.value = response.toString()
        }
    }

}