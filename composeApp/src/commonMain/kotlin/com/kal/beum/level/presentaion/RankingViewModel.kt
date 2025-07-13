package com.kal.beum.level.presentaion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kal.beum.core.domain.onError
import com.kal.beum.core.domain.onSuccess
import com.kal.beum.level.domain.RankerUserInfoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class RankingViewModel(private val rankerUserInfoRepository: RankerUserInfoRepository) : ViewModel() {
    private val _state = MutableStateFlow(RankingState())
    val state = _state.onStart {}.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000L), _state.value
    )

    fun getRankerList(isDevil: Boolean) {
        println("getRankerList")
        viewModelScope.launch {
            rankerUserInfoRepository.getRankerList(isDevil).onSuccess { result ->
                println("Ranker list fetched successfully: ${result.size} items")
                _state.value = _state.value.copy(rankerUserList = result)
            }.onError { error ->
                println("Error fetching ranker list: $error")
            }
        }
    }
}