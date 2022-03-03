package org.quaerense.alef

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.quaerense.alef.network.ApiFactory

class MainViewModel : ViewModel() {

    fun loadData(): List<String> {
        val apiService = ApiFactory.apiService
        val data = mutableListOf<String>()

        viewModelScope.launch {
            val jsonArray = apiService.getImages()
            for (imgUrl in jsonArray) {
                data.add(imgUrl.asString)
            }
        }

        return data
    }
}