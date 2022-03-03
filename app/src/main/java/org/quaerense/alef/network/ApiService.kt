package org.quaerense.alef.network

import com.google.gson.JsonArray
import retrofit2.http.GET

interface ApiService {

    @GET("list.php")
    suspend fun getImages(): JsonArray
}