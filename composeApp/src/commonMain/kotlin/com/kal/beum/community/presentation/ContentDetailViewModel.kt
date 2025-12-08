package com.kal.beum.community.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kal.beum.common.BeumConstants
import com.kal.beum.content.domain.CommentDetail
import com.kal.beum.content.domain.ContentDetail
import com.kal.beum.content.domain.ContentsRepository
import com.kal.beum.core.data.AppUserCache
import com.kal.beum.core.domain.DataError
import com.kal.beum.core.domain.Result
import com.kal.beum.core.domain.onError
import com.kal.beum.core.domain.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ContentDetailViewModel(private val contentDetailRepository: ContentsRepository) :
    ViewModel() {
    private val _state = MutableStateFlow(ContentDetailState())
    val state = _state.onStart {}.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000L), _state.value
    )

    fun sendReply(reply: String) {
        viewModelScope.launch {
            val id = state.value.contentDetail?.id
            if (id != null) {
                contentDetailRepository.sendReply(
                    id, reply, depth = 0, parentId = null, devil = AppUserCache.isDevil
                ).onEach { result ->
                    when (result) {
                        is Result.Error<DataError.Remote> -> {
                            _state.update { it.copy(isProgress = false) }
                        }

                        is Result.Progress -> {
                            _state.update { it.copy(isProgress = true) }
                        }

                        is Result.Success<CommentDetail> -> {
                            _state.update { it.copy(isProgress = false) }
                            _state.update { state ->
                                state.copy(
                                    contentDetail = state.contentDetail?.copy(
                                        commentInfo = state.contentDetail.commentInfo.copy(
                                            comments = state.contentDetail.commentInfo.comments + result.data
                                        )
                                    )
                                )
                            }
                        }
                    }
                }.launchIn(viewModelScope)
            } else {
                // id is null
            }
        }
    }

    fun updateContentDetail(commentDetail: CommentDetail) {
        _state.update { state ->
            state.copy(
                contentDetail = state.contentDetail?.copy(
                    commentInfo = state.contentDetail.commentInfo.copy(
                        comments = state.contentDetail.commentInfo.comments.map {
                            if (it.id == commentDetail.id) commentDetail else it
                        })
                )
            )
        }
    }

    fun reportUser(reasonId: Int) {
        val contentDetail = state.value.contentDetail
        contentDetail?.let { it ->
            viewModelScope.launch {
                contentDetailRepository.reportUser(
                    it.id,
                    BeumConstants.CONTENT_REPORT_REASONS[reasonId],
                    reasonId,
                    "USER",
                    it.writerId
                ).onEach { result ->
                    when (result) {
                        is Result.Error<DataError.Remote> -> {
                            println("reportUser error")
                            _state.update { it.copy(reportMessage = "User 신고 실패 ") }
                        }

                        is Result.Progress -> {
                        }

                        is Result.Success<Boolean> -> {
                            println("reportUser success")
                            _state.update { it.copy(reportMessage = "User 신고 완료") }
                        }
                    }
                }.launchIn(viewModelScope)
            }
        }
    }

    fun reportContent(reasonId: Int) {
        val contentDetail = state.value.contentDetail
        contentDetail?.let {
            viewModelScope.launch {
                contentDetailRepository.reportContent(
                    it.id,
                    BeumConstants.CONTENT_REPORT_REASONS[reasonId],
                    reasonId,
                    "BOARD",
                    it.writerId
                ).onEach { result ->
                    when (result) {
                        is Result.Error<DataError.Remote> -> {
                            println("error~!")
                            _state.update { data -> data.copy(reportMessage = "콘텐츠 신고 실패 ") }
                        }

                        is Result.Progress -> {
                            println("reportContent Result.Progress : ")
                        }

                        is Result.Success<Boolean> -> {
                            _state.update { data -> data.copy(reportMessage = "콘텐츠 신고 완료") }
                        }
                    }
                }.launchIn(viewModelScope)
            }
        }
    }

    fun getContentDetail(id: Int) {
        println("getContentDetail : $id")
        viewModelScope.launch {
            contentDetailRepository.getContentInfo(id).onEach { result ->
                when (result) {
                    is Result.Error<DataError.Remote> -> {
                        _state.update { it.copy(isProgress = false) }
                    }

                    is Result.Progress -> {
                        _state.update { it.copy(isProgress = true) }
                    }

                    is Result.Success<ContentDetail> -> {
                        println("Result.Success : ${result.data}")
                        _state.update {
                            it.copy(
                                contentDetail = result.data, isProgress = false
                            )
                        }
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun selectComment(commentDetail: CommentDetail? = null) {
        println("selectComment : $commentDetail")
        _state.update { it.copy(selectedCommentDetail = commentDetail) }
    }

    fun likeContent(contentDetail: ContentDetail) {
        println("likeContent : $contentDetail")
        viewModelScope.launch {
            contentDetailRepository.likeBoardToggle(contentDetail).onSuccess { contentDetail ->
                println("likeContent : $contentDetail")
                _state.update {
                    it.copy(contentDetail = contentDetail)
                }
            }.onError {
                _state.update { it.copy(isError = true) }
            }
        }
    }

    private fun loadMoreComments() {

    }

    fun likeComment(commentDetail: CommentDetail) {
        viewModelScope.launch {
            contentDetailRepository.likeCommentToggle(commentDetail).onSuccess { result ->
                _state.update { state ->
                    state.copy(
                        contentDetail = state.contentDetail?.copy(
                            commentInfo = state.contentDetail.commentInfo.copy(
                                comments = state.contentDetail.commentInfo.comments.map {
                                    if (it.id == result.id) result else it
                                })
                        )
                    )
                }
            }.onError {
                _state.update { it.copy(isError = true) }
            }
        }
    }

    fun onAction(action: CommunityAction) {
        when (action) {
            is CommunityAction.OnContentLikeClicked -> {
                likeContent(action.contentDetail)
            }

            is CommunityAction.OnTabSelected -> {
            }

            is CommunityAction.OnCommentLikeClicked -> {
                likeComment(action.commentDetail)
            }

            is CommunityAction.LoadMoreComments -> {
                loadMoreComments()
            }
        }
    }

    fun clearToast() {
        _state.update { it.copy(reportMessage = null) }
    }
}