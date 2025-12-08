package com.kal.beum.notice.presentaion

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import beumproject.composeapp.generated.resources.Pretendard_Medium
import beumproject.composeapp.generated.resources.Res
import beumproject.composeapp.generated.resources.angel_abled
import beumproject.composeapp.generated.resources.icon_arrow_right_black
import beumproject.composeapp.generated.resources.sf_pro
import com.kal.beum.core.presentation.BeumColors
import com.kal.beum.core.presentation.BeumDimen
import com.kal.beum.core.presentation.BeumTypo
import com.kal.beum.core.presentation.ToastInfo
import com.kal.beum.main.presentation.MainAction
import com.kal.beum.utils.formatTimeAgoFromLong
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Preview
@Composable
fun NoticeScreen(action: (MainAction) -> Unit) {
    val viewModel = koinViewModel<NoticeViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val topSpace = WindowInsets.safeDrawing
        .asPaddingValues()
        .calculateTopPadding()
    action(MainAction.ToastMessage(ToastInfo("알림", 5000)))

    action(MainAction.OnBackKey { action(MainAction.PopFullScreen) })
    Column(Modifier.fillMaxSize().background(BeumColors.White)) {
        Spacer(modifier = Modifier.height(topSpace))
        Column(
            modifier = Modifier.background(
                BeumColors.White
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically, modifier = Modifier.background(
                    BeumColors.White
                ).fillMaxWidth()
            ) {
                Box(modifier = Modifier.size(48.dp).clickable {
                    action(MainAction.PopFullScreen)
                }, contentAlignment = Alignment.Center) {
                    Image(
                        painter = painterResource(Res.drawable.icon_arrow_right_black),
                        contentDescription = "",
                        modifier = Modifier.graphicsLayer {
                            scaleX = -1f // 좌우 반전!
                        }.size(24.dp)
                    )
                }

                Text(
                    text = "알림", style = TextStyle(
                        fontSize = 20.sp,
                        lineHeight = BeumTypo.baseLineheightTextLineheight300,
                        fontFamily = FontFamily(Font(Res.font.Pretendard_Medium)),
                        fontWeight = FontWeight(BeumTypo.baseTextWeightTextWeight700),
                        color = BeumColors.Black,
                        textAlign = TextAlign.Center
                    )
                )
            }
            Box(
                modifier = Modifier.fillMaxWidth().height(1.dp)
                    .background(BeumColors.baseGrayLightGray75)
            )

            Spacer(modifier = Modifier.height(14.dp))

            LazyRow(
                modifier = Modifier.height(65.dp).fillMaxWidth().padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp) // 👉 간격 추가
            ) {
                items(state.categoryList.size) { index ->
                    val item = state.categoryList[index]
                    val isSelected = state.selectedIndex == index
                    Box(
                        modifier = Modifier.background(
                            color = if (isSelected) BeumColors.GrayGray900 else BeumColors.White,
                            shape = RoundedCornerShape(size = 83.33333.dp)
                        ).border(
                            width = 1.dp,
                            color = if (isSelected) BeumColors.GrayGray900 else BeumColors.GrayGray500,
                            shape = RoundedCornerShape(size = 83.33333.dp)
                        ).padding(vertical = 10.dp, horizontal = 10.dp).clickable {
                            viewModel.onAction(NoticeAction.FilterNotice(index))
                        }, contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = item.category, style = TextStyle(
                                fontSize = 12.sp,
                                lineHeight = 14.17.sp,
                                fontFamily = FontFamily(Font(Res.font.sf_pro)),
                                fontWeight = FontWeight(600),
                                color = if (isSelected) BeumColors.White else BeumColors.baseGrayLightGray500,
                                textAlign = TextAlign.Center,
                            )
                        )
                    }
                }
            }

            LazyColumn {
                items(state.filteredNoticeList.size) {
                    val item = state.filteredNoticeList[it]
                    Column(
                        modifier = Modifier.border(
                            width = 1.dp, color = BeumColors.baseAlphaWhiteDarkWhite50A
                        ).fillMaxWidth().height(100.dp).padding(
                            start = BeumDimen.Px125RemSpacing08,
                            top = 16.dp,
                            end = BeumDimen.Px125RemSpacing08,
                            bottom = 20.dp
                        )
                    ) {

                        Row {
                            Image(
                                painter = painterResource(Res.drawable.angel_abled),
                                contentDescription = ""
                            )

                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Row {
                                    Text(
                                        text = item.category, style = TextStyle(
                                            fontSize = 13.sp,
                                            lineHeight = BeumTypo.lineHeightCaption1,
                                            fontFamily = FontFamily(Font(Res.font.sf_pro)),
                                            fontWeight = FontWeight(400),
                                            color = BeumColors.GrayGray500,
                                        )
                                    )

                                    Spacer(modifier = Modifier.weight(1f))

                                    Text(
                                        text = formatTimeAgoFromLong(item.createdAt), style = TextStyle(
                                            fontSize = 13.sp,
                                            lineHeight = BeumTypo.lineHeightCaption1,
                                            fontFamily = FontFamily(Font(Res.font.sf_pro)),
                                            fontWeight = FontWeight(400),
                                            color = BeumColors.baseGrayLightGray400,
                                        )
                                    )
                                }
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = item.content, style = TextStyle(
                                        fontSize = BeumTypo.TypoScaleText200,
                                        lineHeight = BeumTypo.lineHeightBody3,
                                        fontFamily = FontFamily(Font(Res.font.sf_pro)),
                                        fontWeight = FontWeight(700),
                                        color = BeumColors.baseAlphaBlackDarkBlack700A,
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}