package com.kal.beum.community.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kal.beum.content.domain.ContentsRepository
import com.kal.beum.core.domain.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ContentDetailViewModel(private val contentDetailRepository: ContentsRepository) :
    ViewModel() {
    private val _state = MutableStateFlow(ContentDetailState())
    val state = _state.onStart {
    }.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000L), _state.value
    )

    fun getContentDetail(id: Int) {
        viewModelScope.launch {
            contentDetailRepository.getContentInfo(id).onSuccess { contentDetail ->
                _state.update { it.copy(contentDetail = contentDetail) }
            }
        }
    }
}