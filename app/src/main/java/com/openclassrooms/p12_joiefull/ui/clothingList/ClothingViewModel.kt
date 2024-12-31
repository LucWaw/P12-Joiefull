package com.openclassrooms.p12_joiefull.ui.clothingList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openclassrooms.p12_joiefull.data.repository.ClothingRepository
import com.openclassrooms.p12_joiefull.domain.Clothing
import com.openclassrooms.p12_joiefull.domain.util.onError
import com.openclassrooms.p12_joiefull.domain.util.onLoading
import com.openclassrooms.p12_joiefull.domain.util.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClothingViewModel @Inject constructor( private val repository: ClothingRepository) : ViewModel() {
    private val _state = MutableStateFlow(ClothingListState())
    val state = _state
        .onStart {
            loadClothing()
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = ClothingListState()
        )


    private val _events = Channel<ClothingListEvent>()
    val events = _events.receiveAsFlow()


    fun onAction(action: ClothingListAction) {
        when (action) {
            is ClothingListAction.OnCoinClick -> {
                // Handle coin click
            }

        }
    }

    private fun loadClothing() {
        viewModelScope.launch {
            repository.callClothingApi().onEach { result ->
                result.onLoading {
                    _state.update { it.copy(isLoading = true) }
                }
                result.onError { error ->
                    _events.send(ClothingListEvent.Error(error))
                    _state.update { it.copy(isLoading = true) }
                }
                result.onSuccess { clothes ->
                    val listOfCategories: MutableList<MutableList<Clothing>> = MutableList(4) { mutableListOf() }

                    clothes.forEach { clothing ->
                        when (clothing.category){
                            "TOPS" -> {
                                listOfCategories[0].add(clothing)
                            }
                            "BOTTOMS" -> {
                                listOfCategories[1].add(clothing)
                            }
                            "ACCESSORIES" -> {
                                listOfCategories[2].add(clothing)
                            }
                            "SHOES" -> {
                                listOfCategories[3].add(clothing)
                            }
                            else -> {
                                isOutOfCategory(clothing, listOfCategories)
                            }
                        }
                    }


                    _state.update {
                        it.copy(
                            isLoading = false,
                            clothing = listOfCategories
                        )
                    }
                }
            }.collect {}
        }
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

