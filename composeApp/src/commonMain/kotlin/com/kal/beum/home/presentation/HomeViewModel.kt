package com.kal.beum.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kal.beum.core.data.AppUserCache
import com.kal.beum.core.domain.onSuccess
import com.kal.beum.home.domain.HomeRepository
import com.kal.beum.main.domain.AppRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val homeRepository: HomeRepository
) : ViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state = _state.onStart {
        println("onStart")
        fetchHomeCommentList()
    }.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000L), _state.value
    )

    fun fetchHomeCommentList() {
        println("fetchHomeCommentList")
        viewModelScope.launch {
            homeRepository.getHomeCommentList(AppUserCache.isDevil).onSuccess { result ->
                println("fetchHomeCommentListResult : $result")
                _state.update { it.copy(homeCommentList = result) }
            }
        }.start()
    }

    fun updateHomeCommentList() {
        viewModelScope.launch {

        }

    }
}