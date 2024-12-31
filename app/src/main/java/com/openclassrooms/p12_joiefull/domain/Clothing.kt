package com.openclassrooms.p12_joiefull.domain

data class Clothing(
    val category: String,
    val id: Int,
    val likes: Int,
    val name: String,
    val originalPrice: Double,
    val picture: Picture,
    val price: Double
) {
    data class Picture(
        val description: String,
        val url: String
    )
}