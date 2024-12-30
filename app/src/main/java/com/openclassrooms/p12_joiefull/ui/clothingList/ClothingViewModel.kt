package com.openclassrooms.p12_joiefull.ui.clothingList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openclassrooms.p12_joiefull.data.repository.ClothingRepository
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

@HiltViewModel
class ClothingViewModel(private val repository: ClothingRepository) : ViewModel() {
    private val _state = MutableStateFlow(ClothingListState())
    val state = _state
        .onStart {
            loadCoins()
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

    private fun loadCoins() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            repository.callClothingApi().onEach { result ->
                result.onLoading {
                    _state.update { it.copy(isLoading = true) }
                }
                result.onError {
                    _events.send(ClothingListEvent.Error(it))
                    _state.update { it.copy(isLoading = false) }
                }
                result.onSuccess { clothings ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            clothings
                        )
                    }
                }
            }
        }
    }
}