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
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.union
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import beumproject.composeapp.generated.resources.Res
import beumproject.composeapp.generated.resources.angel_abled
import beumproject.composeapp.generated.resources.ic_angel_emoji
import beumproject.composeapp.generated.resources.ic_close
import beumproject.composeapp.generated.resources.ic_dot
import beumproject.composeapp.generated.resources.ic_more_medium
import beumproject.composeapp.generated.resources.ic_reply
import beumproject.composeapp.generated.resources.ic_small_heart
import beumproject.composeapp.generated.resources.sf_pro
import com.kal.beum.common.BeumConstants
import com.kal.beum.content.domain.CommentDetail
import com.kal.beum.core.presentation.BeumColors
import com.kal.beum.core.presentation.BeumDimen
import com.kal.beum.core.presentation.BeumTypo
import com.kal.beum.main.presentation.FullScreenType
import com.kal.beum.main.presentation.MainAction
import com.kal.beum.myinfo.presentation.ReportBottomSheetPage
import com.kal.beum.myinfo.presentation.ReportDetailBottomSheet
import com.kal.beum.myinfo.presentation.ReportUserBottomSheet
import com.kal.beum.utils.timeAgoFromIsoString
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun ReplyDetailView(
    canSelected: Boolean,
    replyInfoParam: CommentDetail,
    action: (MainAction) -> Unit,
    backBtnClick: (replyInfo: CommentDetail) -> Unit
) {
    val viewModel = koinViewModel<ReplyDetailViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()

    var reportButton by remember { mutableStateOf(false) }
    var reportDetailButton by remember { mutableStateOf(false) }
    var reportUserButton by remember { mutableStateOf(false) }
    var replyOptionButton by remember { mutableStateOf(false) }

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false
    )

    LaunchedEffect(reportDetailButton) {
        if (reportDetailButton) {
            sheetState.expand()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.initReplyInfo(replyInfoParam)
        viewModel.getReplyList(replyInfoParam.boardId, replyInfoParam.id)
    }

    val replyInfo = state.replyInfo ?: return

    Box(
        modifier = Modifier.fillMaxSize().background(Color.White)
            .padding(top = WindowInsets.systemBars.asPaddingValues().calculateTopPadding())
    ) {
        Column(modifier = Modifier.matchParentSize()) {
            Row(
                modifier = Modifier.height(50.dp).padding(start = 20.dp, end = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier.size(24.dp).clickable { backBtnClick(replyInfo) },
                    painter = painterResource(Res.drawable.ic_close),
                    contentDescription = ""
                )

                Spacer(modifier = Modifier.width(22.dp))
                Text(
                    text = "댓글", style = TextStyle(
                        fontSize = 20.sp,
                        lineHeight = BeumTypo.baseLineheightTextLineheight300,
                        fontFamily = FontFamily(Font(Res.font.sf_pro)),
                        fontWeight = FontWeight(BeumTypo.baseTextWeightTextWeight700),
                        color = BeumColors.Black,
                    )
                )
            }

            Box(
                modifier = Modifier.height(1.dp).fillMaxWidth()
                    .background(BeumColors.baseGrayLightGray100)
            ) // Divider

            Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier.border(
                            width = 0.625.dp,
                            color = BeumColors.baseAlphaWhiteLightWhite500A,
                            shape = RoundedCornerShape(size = 20.dp)
                        ).width(24.dp).height(24.dp).background(
                            color = Color(0xFF45CAF7), shape = RoundedCornerShape(size = 20.dp)
                        )
                    ) {
                        Image(
                            painter = painterResource(Res.drawable.ic_angel_emoji),
                            contentDescription = "",
                            modifier = Modifier.size(40.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = replyInfo.user.nickname, style = TextStyle(
                            fontSize = BeumTypo.TypoScaleText100,
                            lineHeight = BeumDimen.lineHeightBody3,
                            fontFamily = FontFamily(Font(Res.font.sf_pro)),
                            fontWeight = FontWeight(600),
                            color = BeumColors.baseGrayLightGray800,
                        )
                    )
                    Spacer(modifier = Modifier.width(6.dp))

                    Text(
                        text = timeAgoFromIsoString(replyInfo.createdAt), style = TextStyle(
                            fontSize = BeumTypo.TypoScaleText100,
                            lineHeight = 20.sp,
                            fontFamily = FontFamily(Font(Res.font.sf_pro)),
                            fontWeight = FontWeight(400),
                            color = BeumColors.baseGrayLightGray500,
                        )
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Image(painter = painterResource(Res.drawable.ic_dot), contentDescription = "")
                    Spacer(modifier = Modifier.width(6.dp))
                    Box {
                        Image(
                            modifier = Modifier.size(24.dp),
                            painter = painterResource(Res.drawable.angel_abled),
                            contentDescription = "",
                        )

//                        Image(
//                            modifier = Modifier.size(12.dp).align(Alignment.BottomStart).clickable {
//                                viewModel.onAction(ReplyAction.OnReplyLikeClicked(replyInfo))
//                            },
//                            painter = painterResource(Res.drawable.ic_small_heart),
//                            contentDescription = ""
//                        )
                    }
                }
                Spacer(modifier = Modifier.fillMaxWidth().height(8.dp))
                Text(
                    modifier = Modifier.padding(horizontal = 14.dp),
                    text = replyInfo.content,
                    style = TextStyle(
                        fontSize = BeumTypo.TypoScaleText150,
                        lineHeight = 22.sp,
                        fontFamily = FontFamily(Font(Res.font.sf_pro)),
                        fontWeight = FontWeight(400),
                        color = BeumColors.baseGrayLightGray800,
                    )
                )

                Spacer(modifier = Modifier.fillMaxWidth().height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 14.dp)
                ) {
                    LikeButton(state.replyInfo?.likeIsMe == true) {
                        viewModel.onAction(ReplyAction.OnReplyLikeClicked(replyInfo))
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = replyInfo.likeCount.toString(), style = TextStyle(
                            fontSize = 13.sp,
                            lineHeight = 20.sp,
                            fontFamily = FontFamily(Font(Res.font.sf_pro)),
                            fontWeight = FontWeight(400),
                            color = BeumColors.GrayGray500,
                        )
                    )
                    Spacer(modifier = Modifier.width(24.dp))

                    Image(painter = painterResource(Res.drawable.ic_reply), contentDescription = "")
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = replyInfo.reReplyCount.toString(), style = TextStyle(
                            fontSize = 13.sp,
                            lineHeight = 20.sp,
                            fontFamily = FontFamily(Font(Res.font.sf_pro)),
                            fontWeight = FontWeight(400),
                            color = BeumColors.GrayGray500,
                        )
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Image(
                        modifier = Modifier.clickable {
                            replyOptionButton = true
                        },
                        painter = painterResource(Res.drawable.ic_more_medium),
                        contentDescription = ""
                    )
                }

            }
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                Modifier.fillMaxWidth().height(10.dp)
                    .background(color = BeumColors.baseCoolGrayLightGray75)
            )
            Spacer(modifier = Modifier.height(16.dp))

            Spacer(modifier = Modifier.width(8.dp))


            Column {
                LazyColumn(Modifier.weight(1f)) {
                    items(replyInfo.replyList.size) {
                        val item = replyInfo.replyList[it]
                        Column(modifier = Modifier.padding(vertical = 8.dp, horizontal = 20.dp)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier.border(
                                        width = 0.625.dp,
                                        color = BeumColors.baseAlphaWhiteLightWhite500A,
                                        shape = RoundedCornerShape(size = 20.dp)
                                    ).width(24.dp).height(24.dp).background(
                                        color = Color(0xFF45CAF7),
                                        shape = RoundedCornerShape(size = 20.dp)
                                    )
                                ) {
                                    Image(
                                        painter = painterResource(Res.drawable.ic_angel_emoji),
                                        contentDescription = "",
                                        modifier = Modifier.size(40.dp)
                                    )
                                }

                                Spacer(modifier = Modifier.width(8.dp))

                                Text(
                                    text = item.user.nickname, style = TextStyle(
                                        fontSize = BeumTypo.TypoScaleText100,
                                        lineHeight = BeumDimen.lineHeightBody3,
                                        fontFamily = FontFamily(Font(Res.font.sf_pro)),
                                        fontWeight = FontWeight(600),
                                        color = BeumColors.baseGrayLightGray800,
                                    )
                                )
                                Spacer(modifier = Modifier.width(6.dp))

                                Text(
                                    text = timeAgoFromIsoString(item.createdAt), style = TextStyle(
                                        fontSize = BeumTypo.TypoScaleText100,
                                        lineHeight = 20.sp,
                                        fontFamily = FontFamily(Font(Res.font.sf_pro)),
                                        fontWeight = FontWeight(400),
                                        color = BeumColors.baseGrayLightGray500,
                                    )
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Image(
                                    painter = painterResource(Res.drawable.ic_dot),
                                    contentDescription = ""
                                )
                                Spacer(modifier = Modifier.width(6.dp))

                                Box {
                                    Image(
                                        modifier = Modifier.size(24.dp),
                                        painter = painterResource(Res.drawable.angel_abled),
                                        contentDescription = "",
                                    )
                                    Image(
                                        modifier = Modifier.size(12.dp)
                                            .align(Alignment.BottomStart),
                                        painter = painterResource(Res.drawable.ic_small_heart),
                                        contentDescription = ""
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.fillMaxWidth().height(8.dp))
                            Text(
                                text = item.content, style = TextStyle(
                                    fontSize = BeumTypo.TypoScaleText150,
                                    lineHeight = 22.sp,
                                    fontFamily = FontFamily(Font(Res.font.sf_pro)),
                                    fontWeight = FontWeight(400),
                                    color = BeumColors.baseGrayLightGray800,
                                )
                            )

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(horizontal = 14.dp)
                            ) {

                                LikeButton(item.likeIsMe) {
                                    viewModel.onAction(ReplyAction.OnSubReplyLikeClicked(item))
                                }

                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = item.likeCount.toString(), style = TextStyle(
                                        fontSize = 13.sp,
                                        lineHeight = 20.sp,
                                        fontFamily = FontFamily(Font(Res.font.sf_pro)),
                                        fontWeight = FontWeight(400),
                                        color = BeumColors.GrayGray500,
                                    )
                                )
                                Spacer(modifier = Modifier.weight(1f))

                                Image(
                                    painter = painterResource(Res.drawable.ic_more_medium),
                                    contentDescription = ""
                                )
                            }
                        }
                    }
                }
                ReplyField {
                    viewModel.onAction(ReplyAction.OnSendReply(replyInfo, it))
                }
                Spacer(modifier = Modifier.windowInsetsPadding(WindowInsets.ime.union(WindowInsets.navigationBars)))
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
                                viewModel.reportContent(BeumConstants.CONTENT_REPORT_REASONS[it])
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
                                viewModel.reportUser(replyInfoParam.user.id)
                            })
                        )
                    )
                }
            }
        }
        if (replyOptionButton) {
            ModalBottomSheet(
                onDismissRequest = {
                    replyOptionButton = false
                },
                sheetState = sheetState,
                dragHandle = null,
                containerColor = BeumColors.baseGrayLightGray75,
                modifier = Modifier.wrapContentHeight().fillMaxWidth()
            ) {
                ReplyOptionBottomSheet(canSelected, onReport = {
                    replyOptionButton = false
                    reportDetailButton = true
                }, onAdopt = {
                    replyOptionButton = false
                    replyInfo.let {
                        viewModel.onAction(
                            ReplyAction.PickComment(
                                it.user.id, it.boardId.toString()
                            )
                        )
                    }
                })
            }
        }
    }
}