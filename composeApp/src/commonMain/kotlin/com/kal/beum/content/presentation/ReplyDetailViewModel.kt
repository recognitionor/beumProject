package com.kal.beum.content.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kal.beum.content.domain.CommentDetail
import com.kal.beum.content.domain.ReplyRepository
import com.kal.beum.core.data.AppUserCache
import com.kal.beum.core.domain.DataError
import com.kal.beum.core.domain.Result
import com.kal.beum.core.domain.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.onSuccess

class ReplyDetailViewModel(private val replyRepository: ReplyRepository) : ViewModel() {
    private val _state = MutableStateFlow(ReplyStateState())

    val state = _state.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000L), _state.value
    )

    fun initReplyInfo(commentDetail: CommentDetail) {
        _state.update { it.copy(replyInfo = commentDetail) }
    }

    fun getReplyList(boardId: Int, commentId: Int) {
        println("getReplyList~~~~~~~~~~~~~~~~~~~~~ : $boardId")
        viewModelScope.launch {
            replyRepository.getReplyList(boardId, commentId).onSuccess { commentInfo ->
                println("getReplyList~~~~~~~~~~~~~~~~~~~~~Result : $commentInfo")
                _state.update {
                    it.copy(
                        replyInfo = state.value.replyInfo?.copy(
                            reReplyCount = commentInfo.commentCount,
                            replyList = commentInfo.comments
                        )
                    )
                }
            }
        }
    }

    fun likeReply(replyDetail: CommentDetail) {
        viewModelScope.launch {
            replyRepository.likeReply(replyDetail).onSuccess { result ->
                _state.update {
                    it.copy(
                        replyInfo = state.value.replyInfo?.copy(
                            likeIsMe = result.likeIsMe, likeCount = result.likeCount
                        )
                    )
                }
            }
        }
    }

    fun likeSubReply(replyDetail: CommentDetail) {
        viewModelScope.launch {
            replyRepository.likeReply(replyDetail).onSuccess { result ->
                _state.update { stateValue ->
                    stateValue.copy(
                        replyInfo = stateValue.replyInfo?.copy(
                            replyList = stateValue.replyInfo.replyList.map { item ->
                                if (item.id == result.id) {
                                    item.copy(
                                        likeIsMe = result.likeIsMe,
                                        likeCount = result.likeCount
                                    )
                                } else {
                                    item
                                }
                            }
                        )
                    )
                }
            }
        }
    }


    fun sendReply(replyDetail: CommentDetail, content: String) {
        println("sendReply : $replyDetail , $content")
        viewModelScope.launch {
            replyRepository.sendReply(
                replyDetail.boardId,
                content,
                replyDetail.depth + 1,
                replyDetail.id,
                AppUserCache.isDevil
            ).onEach { result ->
                when (result) {
                    is Result.Error<DataError.Remote> -> {
                    }

                    is Result.Progress -> {
                    }

                    is Result.Success<Boolean> -> {
                        // Optionally update state with new reply
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun onAction(action: ReplyAction) {
        when (action) {
            is ReplyAction.OnReplyLikeClicked -> likeReply(action.replyDetail)

            is ReplyAction.OnSendReply -> sendReply(action.replyDetail, action.content)

            is ReplyAction.OnSubReplyLikeClicked -> likeSubReply(action.replyDetail)
        }
    }
}
