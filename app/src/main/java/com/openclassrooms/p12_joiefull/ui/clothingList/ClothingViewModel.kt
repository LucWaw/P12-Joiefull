package com.openclassrooms.p12_joiefull.ui.clothingList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openclassrooms.p12_joiefull.data.repository.ClothingRepository
import com.openclassrooms.p12_joiefull.domain.Clothing
import com.openclassrooms.p12_joiefull.domain.util.PossibleError
import com.openclassrooms.p12_joiefull.domain.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClothingViewModel @Inject constructor(private val repository: ClothingRepository) :
    ViewModel() {

    private val _state: MutableStateFlow<ClothingListState> = MutableStateFlow(ClothingListState())
    val state: StateFlow<ClothingListState> = _state.asStateFlow()

    private val _selectedClothing: MutableStateFlow<Clothing?> = MutableStateFlow(null)
    val selectedClothing: StateFlow<Clothing?> = _selectedClothing.asStateFlow()


    private val _events = Channel<ClothingListEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action: ClothingListAction) {
        when (action) {
            is ClothingListAction.OnClothingClick -> {
                _selectedClothing.update { action.clothing }
            }


            is ClothingListAction.OnLikeClick -> {

                val updatedClothing = _state.value.clothing.map { categoryList ->
                    categoryList.map { clothing ->

                        if (clothing.id == action.clothing.id) {
                            if (clothing.isLiked) {
                                clothing.copy(likes = clothing.likes - 1, isLiked = false)
                            } else {
                                clothing.copy(likes = clothing.likes + 1, isLiked = true)
                            }
                        } else {
                            clothing
                        }

                    }.toMutableList()
                }

                _state.update { currentState ->
                    currentState.copy(clothing = updatedClothing)
                }
                _selectedClothing.update {
                    state.value.clothing.flatten().find { it.id == action.clothing.id }
                }
            }

            is ClothingListAction.OnAddReviewClick -> {

                val updatedReviews = action.clothing.getReviewsWithNewReview(action.review, 0)
                val updatedClothing = _state.value.clothing.map { categoryList ->
                    categoryList.map { clothing ->
                        if (clothing.id == action.clothing.id) {
                            clothing.copy(reviews = updatedReviews.first)
                        } else {
                            clothing
                        }
                    }.toMutableList()
                }
                _state.update { currentState ->
                    currentState.copy(clothing = updatedClothing)
                }
                _selectedClothing.update {
                    state.value.clothing.flatten().find { it.id == action.clothing.id }
                }
                if (updatedReviews.second != null && updatedReviews.second!!.name == PossibleError.RATING_ERROR.name) {
                    //call _events.send in a coroutine

                    viewModelScope.launch {
                        _events.send(ClothingListEvent.Error(PossibleError.RATING_ERROR))
                    }

                }
            }
        }
    }

    fun loadClothing(): Flow<Result<List<Clothing>, PossibleError>> {
        return repository.callClothingApi()
            .onEach { result ->
                when (result) {
                    is Result.Loading -> {
                        _state.update { currentState ->
                            currentState.copy(isLoading = true)
                        }
                    }

                    is Result.Error -> {
                        _events.send(ClothingListEvent.Error(result.error))

                        _state.update { currentState ->
                            currentState.copy(isLoading = true)
                        }
                    }

                    is Result.Success -> {
                        val listOfCategories = categorizeClothing(result.data)
                        _state.update { currentState ->
                            currentState.copy(isLoading = false, clothing = listOfCategories)
                        }
                    }
                }
            }
    }

    private fun categorizeClothing(clothes: List<Clothing>): List<MutableList<Clothing>> {
        val listOfCategories = MutableList(4) { mutableListOf<Clothing>() }
        clothes.forEach { clothing ->
            when (clothing.category) {
                "TOPS" -> listOfCategories[0].add(clothing)
                "BOTTOMS" -> listOfCategories[1].add(clothing)
                "ACCESSORIES" -> listOfCategories[2].add(clothing)
                "SHOES" -> listOfCategories[3].add(clothing)
                else -> isOutOfCategory(clothing, listOfCategories)
            }
        }
        return listOfCategories
    }
}


private fun isOutOfCategory(
    clothing: Clothing,
    listOfCategories: MutableList<MutableList<Clothing>>
) {
    if (listOfCategories.isEmpty()) {
        //if the list is empty, add the first category
        listOfCategories.add(mutableListOf(clothing))
    } else {
        var isCategoryFound = false
        for (category in listOfCategories) {
            // if the category is already in the list, add the clothing to the category
            if (category[0].category == clothing.category) { //all the clothing in the same category have the same category name
                category.add(clothing)
                isCategoryFound = true
                break
            }
        }
        // if the category is not in the list, add the category to the list
        if (!isCategoryFound) {
            listOfCategories.add(mutableListOf(clothing))
        }
    }
}

