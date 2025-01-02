package com.openclassrooms.p12_joiefull.ui.clothingList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openclassrooms.p12_joiefull.data.repository.ClothingRepository
import com.openclassrooms.p12_joiefull.domain.Clothing
import com.openclassrooms.p12_joiefull.domain.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ClothingViewModel @Inject constructor(private val repository: ClothingRepository) :
    ViewModel() {

    val state: StateFlow<ClothingListState> = loadClothing()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = ClothingListState()
        )

    private val _selectedClothing : MutableStateFlow<Clothing?> = MutableStateFlow(null)
    val selectedClothing: StateFlow<Clothing?> = _selectedClothing.asStateFlow()


    private val _events = Channel<ClothingListEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action: ClothingListAction) {
        when (action) {
            is ClothingListAction.OnCoinClick -> {
                //change selected clothing
                _selectedClothing.update {
                    action.clothing
                }
            }
        }
    }

    private fun loadClothing(): Flow<ClothingListState> {
        return repository.callClothingApi()
            .map { result ->
                when (result) {
                    is Result.Loading -> {
                        ClothingListState(isLoading = true)
                    }

                    is Result.Error -> {
                        _events.send(ClothingListEvent.Error(result.error))

                        ClothingListState(isLoading = true)
                    }

                    is Result.Success -> {
                        val listOfCategories = categorizeClothing(result.data)
                        ClothingListState(
                            isLoading = false,
                            clothing = listOfCategories
                        )
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

