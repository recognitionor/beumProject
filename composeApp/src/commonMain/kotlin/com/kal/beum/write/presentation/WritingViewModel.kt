package com.kal.beum.write.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kal.beum.core.domain.onError
import com.kal.beum.core.domain.onSuccess
import com.kal.beum.write.domain.WritingCategoryRepository
import com.kal.beum.write.domain.WritingRepository
import com.kal.beum.write.domain.WritingInfoRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WritingViewModel(
    private val writingRepository: WritingRepository,
    private val writeCategoryRepository: WritingCategoryRepository
) : ViewModel() {
    private val _state = MutableStateFlow(WritingState())
    val state = _state.onStart {
        getWriteCategoryList()
    }.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000L), _state.value
    )

    private fun getWriteCategoryList() {
        viewModelScope.launch {
            writeCategoryRepository.getWriteCategory().onSuccess { result ->
                _state.update { it.copy(writeCategoryMap = result) }
            }.onError {

            }
        }.start()
    }

    private fun parseTagsFromString(tagString: String): List<String> {
        return tagString.split("#").map { it.trim() }.filter { it.isNotEmpty() }
    }

    private fun submit() {
        viewModelScope.launch {
            _state.update { it.copy(submitProgress = true) }

            println("tags : ${state.value.tags}")
            val categoryName = state.value.selectedCategory?.category ?: ""
            val writingSubmitRequest = WritingInfoRequest(
                title = state.value.title,
                content = state.value.content,
                tags = parseTagsFromString(state.value.tags),
                isDevil = state.value.isDevil,
                categoryId = state.value.selectedCategory?.categoryId ?: -1,
                categoryName = categoryName,
                rewardPoint = state.value.rewardPoint
            )
            writingRepository.submitWriting(writingSubmitRequest).onSuccess { result ->
                if (result) {
                    _state.update {
                        it.copy(
                            submitProgress = false, isClose = true, closeMessage = "게시글이 등록되었습니다."
                        )
                    }
                }
            }.onError { error ->
                _state.update {
                    it.copy(
                        submitProgress = false, isClose = true, closeMessage = error.name
                    )
                }
            }
        }.start()
    }

    fun onAction(action: WritingAction) { // 이름을 onEvent 대신 onAction으로 변경
        when (action) {
            is WritingAction.OnTitleChanged -> {
                _state.update { it.copy(title = action.newTitle) }
                viewModelScope.launch {
                    writingRepository.saveTempWritingTitle(action.newTitle)
                }
            }

            is WritingAction.OnContentChanged -> {
                _state.update { it.copy(content = action.newContent) }
                viewModelScope.launch {
                    writingRepository.saveTempWritingContent(action.newContent)
                }
            }

            is WritingAction.OnTagChanged -> {
                _state.update { it.copy(tags = action.newTag) }
                viewModelScope.launch {
                    writingRepository.saveTempWritingTags(action.newTag)
                }
            }
            // 다른 WriteAction 처리 로직 추가
            is WritingAction.OnCategoryChanged -> {
                _state.update { it.copy(selectedCategory = action.writingCategory) }
                viewModelScope.launch {
                    writingRepository.saveTempWritingCategory(action.writingCategory)
                }
            }

            is WritingAction.OnCommunityChanged -> {
                println("action.isDevil : ${action.isDevil}")
                _state.update { it.copy(isDevil = action.isDevil) }
                viewModelScope.launch {
                    writingRepository.saveTempWritingIsDevil(action.isDevil)
                }
            }

            is WritingAction.OnPointChanged -> {
                _state.update { it.copy(rewardPoint = action.point) }
                viewModelScope.launch {
                    writingRepository.saveTempWritingRewardPoint(action.point)
                }
            }

            is WritingAction.Reset -> {
                _state.update { WritingState(it.writeCategoryMap, closeMessage = null) }
            }

            is WritingAction.Submit -> {
                submit()
            }

            is WritingAction.Close -> {
                _state.update { it.copy(isClose = true, closeMessage = null) }
            }

            is WritingAction.InitTempWriting -> {
                _state.update {
                    it.copy(
                        title = action.writingData.title,
                        content = action.writingData.content,
                        tags = action.writingData.tags,
                        isDevil = action.writingData.devil,
                        selectedCategory = action.writingData.category,
                        rewardPoint = action.writingData.rewardPoint
                    )
                }
            }

            WritingAction.SaveTempWriting -> {
                val isEmpty =
                    state.value.tags.isEmpty() && state.value.title.isEmpty() && state.value.content.isEmpty() && state.value.selectedCategory == null
                if (isEmpty) {
                    viewModelScope.launch {
                        println("clearTempWritingTitle")
                        writingRepository.clearTempWritingTitle()
                    }
                }
            }
        }
    }
}