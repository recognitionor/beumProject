package com.kal.beum.notice.presentaion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kal.beum.core.domain.onError
import com.kal.beum.core.domain.onSuccess
import com.kal.beum.notice.domain.NoticeCategory
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
        getNoticeList()
    }.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000L), _state.value
    )

    fun getNoticeList() {
        viewModelScope.launch {
            noticeRepository.getNoticeMap().onSuccess { result ->
                println("result : $result")

                // 1) 맵의 키들로 NoticeCategory 리스트 만들기
                val categoriesFromMap = result.noticeMap.keys.mapIndexed { index, key ->
                    NoticeCategory(
                        id = index + 1, category = key
                    )
                }.toMutableList().apply { add(0, NoticeCategory(id = 0, category = "전체")) }

                // 2) NoticeMap 안의 NoticeData 들을 전부 펼쳐서 하나의 리스트로 만들기
                val allNotices = result.noticeMap.values.flatten()

                // 3) 상태 업데이트
                _state.update { old ->
                    old.copy(
                        categoryList = categoriesFromMap,      // 맵의 키를 카테고리로
                        noticeMap = result,                    // 맵 자체 저장
                        noticeList = allNotices,               // 전체 공지 리스트
                        filteredNoticeList = allNotices        // 초기에는 전체 노출
                    )
                }
            }.onError { error ->
                println("error : $error")
            }
        }.start()
    }

    fun filterNoticeList(categoryName: String) {
        viewModelScope.launch {
            val filtered = if (categoryName == "전체") {
                state.value.noticeList
            } else {
                state.value.noticeList.filter { it.noticeType == categoryName }
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