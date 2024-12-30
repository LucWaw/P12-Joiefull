package com.openclassrooms.p12_joiefull.data.response


import com.openclassrooms.p12_joiefull.domain.ClothingResponseModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

class ResponseApiClothing : ArrayList<ResponseApiClothing.ResponseApiClothingItem>(){
    @JsonClass(generateAdapter = true)
    data class ResponseApiClothingItem(
        @Json(name = "category")
        val category: String,
        @Json(name = "id")
        val id: Int,
        @Json(name = "likes")
        val likes: Int,
        @Json(name = "name")
        val name: String,
        @Json(name = "original_price")
        val originalPrice: Double,
        @Json(name = "picture")
        val picture: Picture,
        @Json(name = "price")
        val price: Double
    ) {
        @JsonClass(generateAdapter = true)
        data class Picture(
            @Json(name = "description")
            val description: String,
            @Json(name = "url")
            val url: String
        )
    }
    fun toClothingResponseModel(): List<ClothingResponseModel> {
        return map {
            ClothingResponseModel(
                category = it.category,
                id = it.id,
                likes = it.likes,
                name = it.name,
                originalPrice = it.originalPrice,
                picture = ClothingResponseModel.Picture(
                    description = it.picture.description,
                    url = it.picture.url
                ),
                price = it.price
            )
        }
    }
}