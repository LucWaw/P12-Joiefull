package com.openclassrooms.p12_joiefull.domain

data class Clothing(
    val category: String,
    val id: Int,
    var likes: Int,
    val name: String,
    val originalPrice: Double,
    val picture: Picture,
    val price: Double,
    val isLiked : Boolean = false
) {
    data class Picture(
        val description: String,
        val url: String
    )
}