package com.kal.beum.main.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kal.beum.main.domain.AppRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class MainViewModel(private val appRepository: AppRepository) : ViewModel() {
    private val _state = MutableStateFlow(MainState())
    val state = _state.onStart {
        println("onStart")
        isOnBoardingDone()
    }.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000L), _state.value
    )

    private fun isOnBoardingDone() {
        appRepository.isOnBoardingDone().onEach { result ->
            println("result~~~~ : $result")
        }.launchIn(viewModelScope)

    }
}