package com.kal.beum.content.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kal.beum.content.domain.ReplyRepository
import com.kal.beum.core.domain.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ReplyDetailViewModel(private val replyRepository: ReplyRepository) : ViewModel() {
    private val _state = MutableStateFlow(ReplyStateState())

    val state = _state.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000L), _state.value
    )

    fun getReplyList(contentId: Int) {
        println("getReplyList")
        viewModelScope.launch {
            replyRepository.getReplyList(contentId).onSuccess { commentInfo ->
                _state.update { it.copy(commentInfo) }
            }
        }
    }
}