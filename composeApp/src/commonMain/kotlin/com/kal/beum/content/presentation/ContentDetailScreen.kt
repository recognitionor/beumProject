package com.kal.beum.content.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import beumproject.composeapp.generated.resources.Res
import beumproject.composeapp.generated.resources.ic_angel_emoji
import beumproject.composeapp.generated.resources.ic_more_vertical_medium
import beumproject.composeapp.generated.resources.ic_reply
import beumproject.composeapp.generated.resources.icon_arrow_right_black
import beumproject.composeapp.generated.resources.sf_pro
import com.kal.beum.community.presentation.CommunityAction
import com.kal.beum.community.presentation.ContentDetailViewModel
import com.kal.beum.content.domain.CommentDetail
import com.kal.beum.content.domain.ContentsRepository
import com.kal.beum.core.presentation.BeumColors
import com.kal.beum.core.presentation.BeumTypo
import com.kal.beum.core.presentation.ToastInfo
import com.kal.beum.main.presentation.FullScreenType
import com.kal.beum.main.presentation.MainAction
import com.kal.beum.myinfo.presentation.ReportBottomSheetPage
import com.kal.beum.myinfo.presentation.ReportDetailBottomSheet
import com.kal.beum.myinfo.presentation.ReportUserBottomSheet
import com.kal.beum.utils.formatTimeAgoFromLong
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentDetailScreen(id: Int, action: (MainAction) -> Unit, backBtnClick: () -> Unit) {
    val contentDetailRepository = koinInject<ContentsRepository>()
    val viewModel = remember { ContentDetailViewModel(contentDetailRepository) }
    var reportButton by remember { mutableStateOf(false) }
    var reportDetailButton by remember { mutableStateOf(false) }
    var reportUserButton by remember { mutableStateOf(false) }
    // 답글 옵션 바텀시트 상태
    var replyOptionButton by remember { mutableStateOf(false) }
    var selectedReplyForOption by remember {
        mutableStateOf<CommentDetail?>(
            null
        )
    } // 선택된 답글

    action(MainAction.OnBackKey { action(MainAction.PopFullScreen) })

    val state by viewModel.state.collectAsStateWithLifecycle()

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false
    )

    LaunchedEffect(reportDetailButton) {
        if (reportDetailButton) {
            sheetState.expand() // 시트 열릴 때 전부 보이도록 확장
        }
    }

    LaunchedEffect(Unit) {
        viewModel.getContentDetail(id)
    }

    if (state.reportMessage?.isNotEmpty() == true) {
        println("toast : " + state.reportMessage!!)
        action(MainAction.ToastMessage(ToastInfo(state.reportMessage!!, 2000)))
        viewModel.clearToast()
    }

    if (state.isError) {
        action(MainAction.PopFullScreen)
        action(MainAction.ToastMessage(ToastInfo("게시글 불러오기에 실패 했습니다.", 2000)))
        return
    }

    if (state.isProgress) {
        action(MainAction.PushFullScreen(FullScreenType.ProgressDialog))
    } else {
        action(MainAction.CloseFullScreen(FullScreenType.ProgressDialog))
    }


    if (state.selectedCommentDetail != null) {
        state.selectedCommentDetail?.let {
            ReplyDetailView(it, action, backBtnClick = {
                viewModel.selectComment()
                viewModel.updateContentDetail(it)
            })
        }
    } else {
        Box(
            modifier = Modifier.fillMaxSize().background(Color.White)
                .padding(top = WindowInsets.systemBars.asPaddingValues().calculateTopPadding())
        ) {
            Column(modifier = Modifier.matchParentSize()) {
                Row(modifier = Modifier.padding(start = 20.dp, end = 20.dp)) {
                    Box(modifier = Modifier.width(24.dp).height(24.dp).clickable {
                        backBtnClick()
                    }) {
                        Image(
                            modifier = Modifier.size(24.dp).scale(scaleX = -1f, scaleY = 1f),
                            painter = painterResource(Res.drawable.icon_arrow_right_black),
                            contentDescription = ""
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))
                    Image(
                        modifier = Modifier.clickable {
                            println("clickable")
                            reportUserButton = true
                        },
                        painter = painterResource(Res.drawable.ic_more_vertical_medium),
                        contentDescription = ""
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                Box(
                    modifier = Modifier.fillMaxWidth().height(1.dp)
                        .background(BeumColors.baseGrayLightGray100).padding(horizontal = 20.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                    Row(modifier = Modifier.wrapContentHeight().fillMaxWidth()) {
                        Box(
                            modifier = Modifier.border(
                                width = 0.625.dp,
                                color = BeumColors.baseAlphaWhiteLightWhite500A,
                                shape = RoundedCornerShape(size = 20.dp)
                            ).width(40.dp).height(40.dp).background(
                                color = Color(0xFF45CAF7), shape = RoundedCornerShape(size = 20.dp)
                            ).padding(
                                start = 8.125.dp, top = 6.875.dp, end = 8.125.dp, bottom = 6.875.dp
                            )
                        ) {
                            Image(
                                painter = painterResource(Res.drawable.ic_angel_emoji),
                                contentDescription = "",
                                modifier = Modifier.size(40.dp).clickable {
                                    reportUserButton = true
                                })
                        }

                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(
                                modifier = Modifier.clickable {
                                    reportUserButton = true
                                }, text = state.contentDetail?.writer ?: "", style = TextStyle(
                                    fontSize = BeumTypo.TypoScaleText150,
                                    fontFamily = FontFamily(Font(Res.font.sf_pro)),
                                    fontWeight = FontWeight(600),
                                    color = BeumColors.baseGrayLightGray800,
                                )
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            state.contentDetail?.lastModifiedTime?.let {
                                Text(
                                    text = formatTimeAgoFromLong(it), style = TextStyle(
                                        fontSize = BeumTypo.TypoScaleText75,
                                        fontFamily = FontFamily(Font(Res.font.sf_pro)),
                                        fontWeight = FontWeight(400),
                                        color = BeumColors.baseGrayLightGray500,
                                    )
                                )
                            }

                        }
                    }
                    Spacer(modifier = Modifier.height(20.dp).fillMaxWidth())

                    Box(
                        modifier = Modifier.width(44.dp).height(20.dp).background(
                            color = BeumColors.baseGrayLightGray75,
                            shape = RoundedCornerShape(size = 4.dp)
                        ).padding(start = 4.dp, top = 2.dp, end = 4.dp, bottom = 2.dp)
                    ) {
                        Text(
                            text = state.contentDetail?.categoryName ?: "", style = TextStyle(
                                fontSize = 9.sp,
                                lineHeight = BeumTypo.lineHeightCaption1,
                                fontFamily = FontFamily(Font(Res.font.sf_pro)),
                                fontWeight = FontWeight(400),
                                color = BeumColors.baseGrayLightGray700,
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = state.contentDetail?.title ?: "", style = TextStyle(
                            fontSize = BeumTypo.TypoScaleText400,
                            lineHeight = BeumTypo.TypoScaleText400,
                            fontFamily = FontFamily(Font(Res.font.sf_pro)),
                            fontWeight = FontWeight(600),
                            color = BeumColors.Black,
                        )
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = state.contentDetail?.content ?: "", style = TextStyle(
                            fontSize = 14.sp,
                            lineHeight = 22.sp,
                            fontFamily = FontFamily(Font(Res.font.sf_pro)),
                            fontWeight = FontWeight(400),
                            color = BeumColors.baseGrayLightGray800,
                        )
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "#연봉협상 #환승이직", style = TextStyle(
                            fontSize = 14.sp,
                            lineHeight = 22.sp,
                            fontFamily = FontFamily(Font(Res.font.sf_pro)),
                            fontWeight = FontWeight(400),
                            color = BeumColors.angelSkyblue,
                        )
                    )
                    Spacer(modifier = Modifier.height(24.dp))

                    Row(
                        modifier = Modifier.height(20.dp).fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        LikeButton(state.contentDetail?.like == true) {
                            state.contentDetail?.let {
                                viewModel.onAction(CommunityAction.OnContentLikeClicked(it))
                            }
                        }
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(
                            text = state.contentDetail?.likeCount?.toString() ?: "0",
                            style = TextStyle(
                                fontSize = 13.sp,
                                lineHeight = 20.sp,
                                fontFamily = FontFamily(Font(Res.font.sf_pro)),
                                fontWeight = FontWeight(400),
                                color = BeumColors.GrayGray500,
                            )
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Image(
                            painter = painterResource(Res.drawable.ic_reply),
                            contentDescription = ""
                        )
                        Text(
                            text = state.contentDetail?.commentInfo?.comments?.size?.toString()
                                ?: "0", style = TextStyle(
                                fontSize = 13.sp,
                                lineHeight = 20.sp,
                                fontFamily = FontFamily(Font(Res.font.sf_pro)),
                                fontWeight = FontWeight(400),
                                color = BeumColors.GrayGray500,
                            )
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        Text(
                            text = "조회 ${state.contentDetail?.viewCount ?: "0"}회",
                            style = TextStyle(
                                fontSize = BeumTypo.TypoScaleText75,
                                fontFamily = FontFamily(Font(Res.font.sf_pro)),
                                fontWeight = FontWeight(400),
                                color = BeumColors.baseGrayLightGray500,
                            )
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    Box(
                        modifier = Modifier.height(1.dp).fillMaxWidth()
                            .background(BeumColors.baseGrayLightGray100)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "답글", style = TextStyle(
                            fontSize = 14.sp,
                            lineHeight = BeumTypo.lineHeightBody3,
                            fontFamily = FontFamily(Font(Res.font.sf_pro)),
                            fontWeight = FontWeight(500),
                            color = BeumColors.baseGrayLightGray800,
                        )
                    )

                    Column {
                        Box(modifier = Modifier.weight(1f)) {
                            CommentListView(state, viewModel, false, true) { reply ->
                                selectedReplyForOption = reply
                                replyOptionButton = true
                            }
                        }

//                        LazyColumn(modifier = Modifier.weight(1f)) {
//                            items(state.contentDetail?.commentInfo?.comments?.size ?: 0) { index ->
//                                val reply = state.contentDetail?.commentInfo?.comments?.get(index)
//                                reply?.let {
//                                    Spacer(modifier = Modifier.height(16.dp))
//                                    ReplyView(it, {
//                                        viewModel.onAction(CommunityAction.OnCommentLikeClicked(it))
//                                    }) { replyList ->
//                                        detailReplyInfo = replyList
//                                    }
//                                }
//                            }
//                        }

                        Box(
                            modifier = Modifier.fillMaxWidth().height(2.dp)
                                .background(BeumColors.baseGrayLightGray100)
                        )
                        Row(
                            modifier = Modifier.height(72.dp).padding(top = 12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Box(
                                modifier = Modifier.border(
                                    width = 0.625.dp,
                                    color = BeumColors.baseAlphaWhiteLightWhite500A,
                                    shape = RoundedCornerShape(size = 20.dp)
                                ).width(30.dp).height(30.dp).background(
                                    color = Color(0xFF45CAF7),
                                    shape = RoundedCornerShape(size = 20.dp)
                                ).padding(
                                    start = 8.125.dp,
                                    top = 6.875.dp,
                                    end = 8.125.dp,
                                    bottom = 6.875.dp
                                )
                            ) {
                                Image(
                                    painter = painterResource(Res.drawable.ic_angel_emoji),
                                    contentDescription = "",
                                    modifier = Modifier.size(30.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            ReplyField {
                                viewModel.sendReply(it)
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
            if (reportButton) {
                ModalBottomSheet(
                    onDismissRequest = {
                        reportButton = false
                    },
                    sheetState = sheetState,
                    containerColor = BeumColors.baseGrayLightGray75,
                    dragHandle = null,
                    modifier = Modifier.wrapContentHeight().fillMaxWidth()
                ) {
                    ReportBottomSheetPage({
                        reportButton = false
                    }) {
                        reportButton = false
                        reportDetailButton = true
                    }
                }
            }
            if (reportDetailButton) {
                ModalBottomSheet(
                    onDismissRequest = {
                        reportDetailButton = false
                    }, // 닫힐 때 None으로 초기화
                    sheetState = sheetState,
                    dragHandle = null,
                    containerColor = BeumColors.baseGrayLightGray75,
                    modifier = Modifier.fillMaxWidth().fillMaxHeight(0.85f)
                ) {
                    ReportDetailBottomSheet {
                        reportDetailButton = false
                        action(
                            MainAction.PushFullScreen(
                                FullScreenType.ReportConfirmDialog("해당 게시물을 신고 하시겠습니까?", {
                                    action(MainAction.PopFullScreen)
                                }, {
                                    viewModel.reportContent(it)
                                    action(MainAction.PopFullScreen)
                                })
                            )
                        )
                    }
                }
            }
            if (reportUserButton) {
                ModalBottomSheet(
                    onDismissRequest = {
                        reportUserButton = false
                    }, // 닫힐 때 None으로 초기화
                    sheetState = sheetState,
                    dragHandle = null,
                    containerColor = BeumColors.baseGrayLightGray75,
                    modifier = Modifier.fillMaxWidth().fillMaxHeight(0.85f)
                ) {
                    ReportUserBottomSheet {
                        reportUserButton = false
                        action(
                            MainAction.PushFullScreen(
                                FullScreenType.ReportConfirmDialog("해당 사용자를 신고 하시겠습니까?", {
                                    action(MainAction.PopFullScreen)
                                }, {
                                    action(MainAction.PopFullScreen)
                                    viewModel.reportUser(it)
                                })
                            )
                        )
                    }
                }
            }
            // 답글 옵션 바텀시트
            if (replyOptionButton) {
                ModalBottomSheet(
                    onDismissRequest = {
                        replyOptionButton = false
                        selectedReplyForOption = null
                    },
                    sheetState = sheetState,
                    dragHandle = null,
                    containerColor = BeumColors.baseGrayLightGray75,
                    modifier = Modifier.wrapContentHeight().fillMaxWidth()
                ) {
                    ReplyOptionBottomSheet(onReport = {
                        replyOptionButton = false
                        reportDetailButton = true
                    }, onAdopt = {
                        println("onAdopt : $selectedReplyForOption")
                        replyOptionButton = false
                        selectedReplyForOption?.let {
                            viewModel.onAction(
                                CommunityAction.PickComment(
                                    it.user.id, it.boardId.toString()
                                )
                            )
                            //action(MainAction.ToastMessage(ToastInfo("채택되었습니다.", 2000))) // 임시 토스트
                        }
                    })
                }
            }
        }
    }
}
