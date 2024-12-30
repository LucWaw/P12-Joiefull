package com.openclassrooms.p12_joiefull.data.network

import com.openclassrooms.p12_joiefull.data.response.ResponseApiClothing
import retrofit2.Response
import retrofit2.http.GET

interface ClothingApi {
    @GET("clothes.json")
    suspend fun getClothes(): Response<ResponseApiClothing>
}