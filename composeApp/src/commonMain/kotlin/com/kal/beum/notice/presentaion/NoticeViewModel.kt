package com.kal.beum.notice.presentaion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kal.beum.core.domain.onError
import com.kal.beum.core.domain.onSuccess
import com.kal.beum.main.presentation.MainAction
import com.kal.beum.notice.domain.NoticeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NoticeViewModel(private val noticeRepository: NoticeRepository) : ViewModel() {
    private val _state = MutableStateFlow(NoticeState())
    val state = _state.onStart {
        getCategory()
        getNoticeList()
    }.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000L), _state.value
    )

    fun getCategory() {
        viewModelScope.launch {
            noticeRepository.getCategoryList().onSuccess { result ->
                println("getCategory result: $result")
                _state.update { it.copy(categoryList = result) }
            }.onError { error ->
                // Handle error if needed
            }
        }.start()
    }

    fun getNoticeList() {
        viewModelScope.launch {
            noticeRepository.getNoticeList().onSuccess { result ->
                _state.update { it.copy(noticeList = result, filteredNoticeList = result) }
            }.onError { error ->
                // Handle error if needed
            }
        }.start()
    }

    fun filterNoticeList(categoryName: String) {
        viewModelScope.launch {
            val filtered = if (categoryName == "전체") {
                state.value.noticeList
            } else {
                state.value.noticeList.filter { it.category == categoryName }
            }
            _state.update { oldState ->
                oldState.copy(filteredNoticeList = filtered)
            }
        }
    }

    fun onAction(action: NoticeAction) {
        when (action) {
            is NoticeAction.FilterNotice -> {
                _state.update { it.copy(selectedIndex = action.index) }
                filterNoticeList(state.value.categoryList[action.index].category)
            }
        }
    }
}