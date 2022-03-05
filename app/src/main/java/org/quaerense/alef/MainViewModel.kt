package org.quaerense.alef

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.quaerense.alef.network.ApiFactory

class MainViewModel : ViewModel() {
    private val _imageUrls = MutableLiveData<List<String>>()
    val imageUrls: LiveData<List<String>>
        get() = _imageUrls

    fun loadData() {
        val apiService = ApiFactory.apiService
        viewModelScope.launch {
            val data = mutableListOf<String>()
            val jsonArray = apiService.getImages()
            for (url in jsonArray) {
                data.add(convertToHttps(url.asString))
            }
            _imageUrls.value = data
        }
    }

    private fun convertToHttps(url: String): String {
        return url.replaceFirst("http://", "https://")
    }
}