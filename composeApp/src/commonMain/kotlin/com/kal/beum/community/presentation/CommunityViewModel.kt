package com.kal.beum.community.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kal.beum.community.domain.CommunityItem
import com.kal.beum.community.domain.CommunityRepository
import com.kal.beum.core.data.AppUserCache
import com.kal.beum.core.domain.onError
import com.kal.beum.core.domain.onProgress
import com.kal.beum.core.domain.onSuccess
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.collections.emptyList

class CommunityViewModel(private val communityRepository: CommunityRepository) : ViewModel() {
    private var isCategoryLoaded: Boolean = false
    private val _state = MutableStateFlow(CommunityState())

    val state = _state.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000L), _state.value
    )

    // 1. 이벤트를 보낼 채널 생성 (Buffer가 없는 파이프라인)
    private val _sideEffect = Channel<CommunitySideEffect>()

    // 2. UI에서는 Flow 형태로 받기 위해 변환 (KMP 호환됨)
    val sideEffect = _sideEffect.receiveAsFlow()

    fun getCategory(isDevil: Boolean) {
        println("getCategory : $isCategoryLoaded - $this")
        viewModelScope.launch {
            communityRepository.getCategoryList().onSuccess { result ->
                // result: CategoryMap (val categoryMap: Map<String, List<Category>>)
                println("result : $result")

                // 1) 맵의 키들(그룹명)을 추출하여 CategoryGroupList 생성
                val categoryGroupList = result.categoryMap.keys.toList()

                // 2) 첫 번째 그룹의 카테고리 리스트 가져오기
                val firstGroupName = categoryGroupList.firstOrNull() ?: ""
                val firstGroupCategories = result.categoryMap[firstGroupName] ?: emptyList()
                val firstCategoryId = firstGroupCategories.firstOrNull()?.id ?: 0

                // 3) communityList 초기화 (각 카테고리별 빈 리스트)
                val tempList: List<List<CommunityItem>> =
                    List(firstGroupCategories.size) { emptyList() }

                // 4) 상태 업데이트 (첫 번째 그룹과 첫 번째 카테고리 선택)
                _state.update { old ->
                    old.copy(
                        categoryOriginalMap = result,
                        categoryGroupList = categoryGroupList,
                        categoryList = firstGroupCategories,
                        communityList = tempList,
                        selectedCategoryGroupId = firstGroupName,
                        selectedCategoryId = firstCategoryId  // 실제 카테고리 ID
                    )
                }

                // 5) 첫 번째 카테고리의 아이템 로드
                getItemsByCategoryTemp(
                    categoryId = firstCategoryId,
                    isDevil
                )
                isCategoryLoaded = true
            }.onError { error ->
                _sideEffect.send(CommunitySideEffect.ShowToast("네트워크 에러"))
            }
        }.start()
    }

    fun getItemsByCategoryTemp(categoryId: Int, isDevil: Boolean) {
        println("getItemsByCategoryTemp~~~~$categoryId")
        viewModelScope.launch {
            communityRepository.getCommunityList(
                state.value.communityListPage, 10, isDevil, categoryId
            ).onEach { result ->
                println("getItemsByCategoryTemp~~~~result ^^ $result")
                result.onSuccess { community ->
                    _state.update { currentState ->
                        currentState.copy(communityListTemp = community.boardList)
                    }
                    println("onSuccess")
                }.onError {
                    _state.update { currentState ->
                        currentState.copy(communityListTemp = emptyList())
                    }
                    println("onError")
                }.onProgress {
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
        viewModelScope.launch {}.start()
    }

    private fun loadMoreComments() {
    }

    private fun pickComment(targetUserId: String, boardId: String) {
        println("private fun pickComment(targetUserId: String, boardId: String)")
        viewModelScope.launch {
            println("launch")
            communityRepository.pickComment(targetUserId, boardId)
        }.start()
    }

    /**
     * 선택된 그룹으로 카테고리 리스트 필터링
     */
    private fun filterCategoriesByGroup(groupName: String) {
        val originalMap = state.value.categoryOriginalMap ?: return

        val filteredCategories = originalMap.categoryMap[groupName] ?: emptyList()
        val filteredCategoryId = originalMap.categoryMap[groupName]?.firstOrNull()?.id ?: 0
        _state.update { old ->
            old.copy(
                categoryList = filteredCategories,
                selectedCategoryId = filteredCategoryId  // 필터 변경 시 첫 번째 카테고리로 리셋
            )
        }
        getItemsByCategoryTemp(filteredCategoryId, AppUserCache.isDevil)
    }

    fun onAction(action: CommunityAction) {
        when (action) {
            is CommunityAction.OnCategoryGroupSelected -> {
                if (state.value.selectedCategoryGroupId == action.categoryGroupName) {
                    return
                }
                _state.update {
                    it.copy(selectedCategoryGroupId = action.categoryGroupName)
                }
                // 그룹 변경 시 카테고리 필터링
                filterCategoriesByGroup(action.categoryGroupName)
            }

            is CommunityAction.OnCategorySelected -> {
                if (state.value.selectedCategoryId == action.index) {
                    return
                }
                _state.update {
                    it.copy(selectedCategoryId = action.index)
                }
                getItemsByCategoryTemp(action.index, action.isDevil)
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

            is CommunityAction.PickComment -> {
                println("is CommunityAction.PickComment -> {")
                pickComment(action.targetUserId, action.boardId)
            }
        }
    }
}