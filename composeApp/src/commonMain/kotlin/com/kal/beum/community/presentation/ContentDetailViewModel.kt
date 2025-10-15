package com.kal.beum.community.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kal.beum.content.domain.CommentDetail
import com.kal.beum.content.domain.CommentInfo
import com.kal.beum.content.domain.ContentDetail
import com.kal.beum.content.domain.ContentsRepository
import com.kal.beum.core.data.AppUserCache
import com.kal.beum.core.domain.DataError
import com.kal.beum.core.domain.Result
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
                        _state.update { it.copy(contentDetail = result.data, isProgress = false) }
                    }
                }
            }.launchIn(viewModelScope)
        }
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

    fun likeComment(commentDetail: CommentDetail) {
        viewModelScope.launch {
            contentDetailRepository.likeCommentToggle(commentDetail).onSuccess { result ->
                _state.update { state ->
                    state.copy(
                        contentDetail = state.contentDetail?.copy(
                            commentInfo = state.contentDetail.commentInfo.copy(
                                comments = state.contentDetail.commentInfo.comments.map {
                                    if (it.id == result.id) result else it
                                }
                            )
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
        }
    }
}