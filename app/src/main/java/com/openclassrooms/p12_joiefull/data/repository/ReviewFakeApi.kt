package com.openclassrooms.p12_joiefull.data.repository

import com.openclassrooms.p12_joiefull.data.response.ResponseFakeReview
import com.openclassrooms.p12_joiefull.domain.Clothing

class ReviewFakeApi {
    private val listOfReview: MutableList<ResponseFakeReview> = mutableListOf(
        ResponseFakeReview(
            review = "Ce jean est très confortable, je le recommande",
            rating = 4,
            clothingId = 1,
            userId = 1
        ),
        ResponseFakeReview(
            review = "Ces bottes sont très jolies, je les adore",
            rating = 5,
            clothingId = 2,
            userId = 1
            ),
        ResponseFakeReview(
            review = "Moyen.",
            rating = 4,
            clothingId = 2,
            userId = 2
        ),
        ResponseFakeReview(
            review = "Bien.",
            rating = 4,
            clothingId = 2,
            userId = 3
        ),
        ResponseFakeReview(
            review = "Ce blazer est très élégant, je le porte pour toutes mes réunions",
            rating = 5,
            clothingId = 3,
            userId = 1),
        ResponseFakeReview(
            review = "Ce pull est très chaud, je le porte tous les jours",
            rating = 4,
            clothingId = 4,
            userId = 1),
        ResponseFakeReview(
            review = "Ces escarpins sont très confortables, je les porte pour toutes mes soirées",
            rating = 5,
            clothingId = 5,
            userId = 1),
        ResponseFakeReview(
            review = "Ce sac à dos est très pratique, je l'utilise pour toutes mes randonnées",
            rating = 4,
            clothingId = 6,
            userId = 1),
        ResponseFakeReview(
            review = "Ce bomber est très tendance, je le porte pour toutes mes sorties",
            rating = 5,
            clothingId = 7,
            userId = 1),
        ResponseFakeReview(
            review = "Ce bomber est très sympatique mais un peu petit",
            rating = 3,
            clothingId = 7,
            userId = 2),
        ResponseFakeReview(
            review = "Ce sweat est très confortable, je le porte pour tous mes week-ends",
            rating = 4,
            clothingId = 8,
            userId = 1),
        ResponseFakeReview(
            review = "Ce t-shirt est très joli, je le porte pour toutes mes sorties",
            rating = 5,
            clothingId = 9,
            userId = 1),
        ResponseFakeReview(
            review = "Ce pendentif est très joli, je le porte pour toutes mes soirées",
            rating = 5,
            clothingId = 10,
            userId = 1),
        ResponseFakeReview(
            review = "Ce pantalon est très confortable, je le porte pour toutes mes sorties",
            rating = 4,
            clothingId = 11,
            userId = 1),
    )

    fun getClothingReviews(idClothing: Int): List<Clothing.Review> {
        val reviews = mutableListOf<Clothing.Review>()
        listOfReview.forEach {
            if (it.clothingId == idClothing) {
                reviews.add(Clothing.Review(it.review, it.rating, it.userId))
            }
        }
        return reviews
    }



}