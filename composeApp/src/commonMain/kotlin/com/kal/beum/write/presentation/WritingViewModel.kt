package com.kal.beum.write.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kal.beum.core.domain.onSuccess
import com.kal.beum.write.domain.WritingCategoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WritingViewModel(private val writeCategoryRepository: WritingCategoryRepository) :
    ViewModel() {
    private val _state = MutableStateFlow(WritingState())
    val state = _state.onStart {
        getWriteCategoryList()
    }.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000L), _state.value
    )

    private fun getWriteCategoryList() {
        println("getWriteCategoryList()")
        viewModelScope.launch {
            writeCategoryRepository.getWriteCategory().onSuccess { result ->
                println("getWriteCategoryList(): $result")
                _state.update { it.copy(writeCategoryMap = result) }
            }
        }.start()

    }

    fun onAction(action: WritingAction) { // 이름을 onEvent 대신 onAction으로 변경
        when (action) {
            is WritingAction.OnTitleChanged -> {
                _state.update { it.copy(title = action.newTitle) }
            }

            is WritingAction.OnContentChanged -> {
                _state.update { it.copy(content = action.newContent) }
            }

            is WritingAction.OnTagChanged -> {
                _state.update { it.copy(tags = action.newTag) }
            }
            // 다른 WriteAction 처리 로직 추가
            is WritingAction.OnCategoryChanged -> {
                _state.update { it.copy(selectedCategory = action.writingCategory) }
            }

            is WritingAction.OnCommunityChanged -> {
                _state.update { it.copy(isDevil = action.isDevil) }
            }

            is WritingAction.OnPointChanged -> {
                _state.update { it.copy(rewardPoint = action.point) }
            }
        }
    }
}