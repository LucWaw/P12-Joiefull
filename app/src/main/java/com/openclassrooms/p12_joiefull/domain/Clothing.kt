package com.openclassrooms.p12_joiefull.domain

import com.openclassrooms.p12_joiefull.data.repository.ReviewFakeApi
import com.openclassrooms.p12_joiefull.domain.util.PossibleError

data class Clothing(
    val category: String,
    val id: Int,
    val likes: Int,
    val name: String,
    val originalPrice: Double,
    val picture: Picture,
    val price: Double,
    val reviews: List<Review> = ReviewFakeApi().getClothingReviews(id).toMutableList(),
    val isLiked: Boolean = false
) {
    data class Picture(
        val description: String,
        val url: String
    )

    data class Review(
        val review: String,
        val rating: Int,
        val userId: Int
    )

    fun getReviewsWithNewReview(review: Review, idUser: Int): Pair<List<Review>, PossibleError?> {
        val updatedReviews = reviews.toMutableList()
        var userAlreadyReview = false

        updatedReviews.forEachIndexed { index, existingReview ->
            if (existingReview.userId == idUser) {
                userAlreadyReview = true
                updatedReviews[index] = review // Replace the review
            }
        }

        if (userAlreadyReview) {
            return Pair(updatedReviews, PossibleError.RATING_ERROR)
        }

        return Pair(updatedReviews + review, null)
    }

    fun getAverageRating(): Double {
        var totalRating = 0
        reviews.forEach {
            totalRating += it.rating
        }
        return totalRating.toDouble() / reviews.size
    }
}


