package com.kal.beum.community.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kal.beum.community.domain.Category
import com.kal.beum.community.domain.CommunityItem
import com.kal.beum.community.domain.CommunityRepository
import com.kal.beum.core.domain.onError
import com.kal.beum.core.domain.onProgress
import com.kal.beum.core.domain.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CommunityViewModel(private val communityRepository: CommunityRepository) : ViewModel() {
    private var isCategoryLoaded: Boolean = false
    private val _state = MutableStateFlow(CommunityState())

    val state = _state.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000L), _state.value
    )

    fun getCategory(isDevil: Boolean) {
        println("getCategory : $isCategoryLoaded - $this")
        viewModelScope.launch {
            communityRepository.getCategoryList().onSuccess { result ->
                val tempList: List<List<CommunityItem>> = List(result.size) { emptyList() }
                _state.update { it.copy(categoryList = result, communityList = tempList) }
                getItemsByCategory(category = result[state.value.selectedCategoryId], isDevil)
                isCategoryLoaded = true
            }
        }.start()
    }

    private fun getItemsByCategory(category: Category, isDevil: Boolean) {
        println("getItemsByCategory")
        viewModelScope.launch {
            communityRepository.getCommunityList(0, 10, isDevil, category).onEach { result ->
                result.onSuccess { community ->
                    _state.update { it -> it.copy(onProgress = true) }
                    println("onSuccess")
                    if (community.boardList.isNotEmpty()) {
                        _state.update { currentState ->
                            // 카테고리 id로 index 찾기
                            val index =
                                currentState.categoryList.indexOfFirst { it.id == category.id }
                            if (index == -1) return@onSuccess

                            // communityList 복사해서 해당 index만 새로운 itemList로 교체
                            val newCommunityList =
                                currentState.communityList.toMutableList().apply {
                                    if (size > index) {
                                        this[index] = community.boardList // index 위치의 리스트만 덮어쓰기
                                    } else {
                                        // 예외처리: communityList의 사이즈가 index보다 작으면, null 리스트로 채우고 마지막에 추가
                                        repeat(index - size) { add(emptyList()) }
                                        add(community.boardList)
                                    }
                                }
                            // 새로운 state로 업데이트
                            currentState.copy(communityList = newCommunityList, onProgress = false)
                        }
                    }
                    println("onSuccess")
                }.onError {
                    _state.update { it.copy(onProgress = false) }
                    println("onError")
                }.onProgress {
                    _state.update { it.copy(onProgress = true) }
                    println("onProgress")
                }
//                _state.update { currentState ->
//                    // 카테고리 id로 index 찾기
//                    val index = currentState.categoryList.indexOfFirst { it.id == category.id }
//                    if (index == -1) return@onSuccess // 해당 카테고리가 없으면 패스
//
//                    // communityList 복사해서 해당 index만 새로운 itemList로 교체
//                    val newCommunityList = currentState.communityList.toMutableList().apply {
//                        if (size > index) {
//                            this[index] = itemList.boardList // index 위치의 리스트만 덮어쓰기
//                        } else {
//                            // 예외처리: communityList의 사이즈가 index보다 작으면, null 리스트로 채우고 마지막에 추가
//                            repeat(index - size) { add(emptyList()) }
//                            add(itemList.boardList)
//                        }
//                    }
//                    // 새로운 state로 업데이트
//                    currentState.copy(communityList = newCommunityList)
//                }
            }.launchIn(this)
        }.start()
    }

    private fun likeComment() {
        viewModelScope.launch {
        }.start()
    }
    private fun loadMoreComments() {
    }

    fun onAction(action: CommunityAction) {
        when (action) {
            is CommunityAction.OnTabSelected -> {
                if (state.value.selectedCategoryId == action.index) {
                    return
                }
                _state.update {
                    it.copy(selectedCategoryId = action.index)
                }
                getItemsByCategory(state.value.categoryList[action.index], action.isDevil)
            }

            is CommunityAction.OnContentLikeClicked -> {
                println("OnContentLikeClicked")
            }

            is CommunityAction.OnCommentLikeClicked -> {
                println("OnCommentLikeClicked")
                likeComment()
            }

            is CommunityAction.LoadMoreComments -> {
                loadMoreComments()
            }
        }
    }
}