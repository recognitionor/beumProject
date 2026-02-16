package com.kal.beum.content.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kal.beum.community.presentation.CommunityAction
import com.kal.beum.community.presentation.ContentDetailState
import com.kal.beum.community.presentation.ContentDetailViewModel
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
fun CommentListView(
    state: ContentDetailState,                 // state.contentDetail?.commentInfo?.comments 등 포함
    viewModel: ContentDetailViewModel,         // onAction(LoadMore) 같은 액션 처리
    isLoading: Boolean,             // 로딩 중 여부
    hasMore: Boolean,
    onReplyOptionClicked: (com.kal.beum.content.domain.CommentDetail) -> Unit,
    headerContent: @Composable () -> Unit = {}
) {
    val comments = state.contentDetail?.commentInfo?.comments.orEmpty()
    val listState = rememberLazyListState()

    // ✅ 끝 근처 진입 시 다음 페이지 로드
    val prefetch = 10 // 끝에서 5개 남기고 미리 로드
    LaunchedEffect(listState, comments.size, isLoading, hasMore) {
        snapshotFlow {
            val lastVisible = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: -1
            val total = listState.layoutInfo.totalItemsCount
            lastVisible to total
        }.distinctUntilChanged().collect { (last, total) ->
            println("~~!~!~!")
            if (!isLoading && hasMore && total > 0 && last >= total - 1 - prefetch) {
                println("trigger")
//                    viewModel.onAction(CommunityAction.LoadMoreComments)
            }
        }
    }

    LazyColumn(
        state = listState,
        contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 20.dp)
    ) {
        item { headerContent() }
        items(count = comments.size, key = { idx -> comments[idx].id } // ✅ 키 지정(스크롤 안정성)
        ) { index ->
            val reply = comments[index]
            Spacer(modifier = Modifier.height(16.dp))
            ReplyView(
                reply,
                likeClicked = {
                    viewModel.onAction(CommunityAction.OnCommentLikeClicked(reply))
                },
                selectedDetailReview = { replyList ->
                    viewModel.selectComment(replyList)
                },
                onOptionClicked = {
                    onReplyOptionClicked(reply)
                })
        }

        // ✅ 로딩 인디케이터(마지막에 붙이기)
        if (isLoading) {
            item(key = "loading") {
                Box(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }

        // ✅ 더 없음 표시(선택)
        if (!hasMore && comments.isNotEmpty()) {
            item(key = "no-more") {
                Box(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("더 이상 댓글이 없어요", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}