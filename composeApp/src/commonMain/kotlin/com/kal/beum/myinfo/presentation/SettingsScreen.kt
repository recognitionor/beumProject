package com.kal.beum.myinfo.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import beumproject.composeapp.generated.resources.Pretendard_Medium
import beumproject.composeapp.generated.resources.Res
import beumproject.composeapp.generated.resources.icon_arrow_right_black
import com.kal.beum.core.presentation.BeumColors
import com.kal.beum.core.presentation.BeumTypo
import com.kal.beum.main.domain.UserInfo
import com.kal.beum.main.presentation.FullScreenType
import com.kal.beum.main.presentation.MainAction
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource

@Composable

fun SettingsScreen(info: UserInfo, action: (MainAction) -> Unit) {
    val topSpace = WindowInsets.safeDrawing.asPaddingValues().calculateTopPadding()
    Column(Modifier.fillMaxSize().background(BeumColors.baseGrayLightGray75)) {
        Spacer(modifier = Modifier.height(topSpace))
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()
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
                    text = "설정", style = TextStyle(
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
                modifier = Modifier.fillMaxWidth().height(1.dp).background(BeumColors.baseGrayLightGray75)
            )

            Box(
                modifier = Modifier.fillMaxWidth().height(56.dp)
                    .padding(start = 20.dp, top = 0.dp, end = 20.dp, bottom = 0.dp).clickable {
                        action(MainAction.PushFullScreen(FullScreenType.MyInfoDetailScreen(info)))
//                        action(MainAction.PushFullScreen {
//                            MyInfoDetailScreen(info, action)
//                        })
                    }, contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = "내 정보", style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 20.96.sp,
                        fontFamily = FontFamily(Font(Res.font.Pretendard_Medium)),
                        fontWeight = FontWeight(700),
                        color = BeumColors.baseGrayLightGray800,
                        textAlign = TextAlign.Center,
                    )
                )
            }

            Box(
                modifier = Modifier.fillMaxWidth().height(56.dp)
                    .padding(start = 20.dp, top = 0.dp, end = 20.dp, bottom = 0.dp).clickable {
                        action(MainAction.PushFullScreen(FullScreenType.ServicePolicyInfoScreen))
                    }, contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = "서비스 정보 및 약관", style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 20.96.sp,
                        fontFamily = FontFamily(Font(Res.font.Pretendard_Medium)),
                        fontWeight = FontWeight(700),
                        color = BeumColors.baseGrayLightGray800,
                        textAlign = TextAlign.Center,
                    )
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth().height(56.dp)
                    .padding(start = 20.dp, top = 0.dp, end = 20.dp, bottom = 0.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "앱 버전", style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 20.96.sp,
                        fontFamily = FontFamily(Font(Res.font.Pretendard_Medium)),
                        fontWeight = FontWeight(700),
                        color = BeumColors.baseGrayLightGray800,
                        textAlign = TextAlign.Center,
                    )
                )
                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "V.1.0.0", style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 20.96.sp,
                        fontFamily = FontFamily(Font(Res.font.Pretendard_Medium)),
                        fontWeight = FontWeight(700),
                        color = BeumColors.baseGrayLightGray600,
                        textAlign = TextAlign.Center,
                    )
                )
            }

            Box(
                modifier = Modifier.clickable {
                    action(MainAction.PushFullScreen(FullScreenType.LogOutDialog({
                        action(MainAction.PopFullScreen)
                    }) {
                        action(MainAction.ClearFullScreen)
                        action(MainAction.LogOut)
                    }))
                }.fillMaxWidth().height(56.dp).padding(start = 20.dp, top = 0.dp, end = 20.dp, bottom = 0.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = "로그아웃", style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 20.96.sp,
                        fontFamily = FontFamily(Font(Res.font.Pretendard_Medium)),
                        fontWeight = FontWeight(700),
                        color = Color(0xFFF73914),
                        textAlign = TextAlign.Center,
                    )
                )
            }
        }
    }
}

