package com.kal.beum.myinfo.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import beumproject.composeapp.generated.resources.apple
import beumproject.composeapp.generated.resources.google
import beumproject.composeapp.generated.resources.icon_arrow_right_black
import beumproject.composeapp.generated.resources.kakao
import beumproject.composeapp.generated.resources.sf_pro
import com.kal.beum.core.presentation.BeumColors
import com.kal.beum.core.presentation.BeumTypo
import com.kal.beum.main.domain.SocialType
import com.kal.beum.main.domain.UserInfo
import com.kal.beum.main.presentation.MainAction
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource

@Composable
fun MyInfoDetailScreen(info: UserInfo, action: (MainAction) -> Unit) {
    Column(modifier = Modifier.background(BeumColors.baseGrayLightGray75).fillMaxSize()) {
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
                    text = "내 정보", style = TextStyle(
                        fontSize = 20.sp,
                        lineHeight = BeumTypo.baseLineheightTextLineheight300,
                        fontFamily = FontFamily(Font(Res.font.sf_pro)),
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

            Row(
                modifier = Modifier.fillMaxWidth().height(56.dp).background(color = BeumColors.White)
                    .padding(start = 20.dp, top = 0.dp, end = 20.dp, bottom = 0.dp), verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "닉네임", style = TextStyle(
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
                    text = info.nickName, style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 20.96.sp,
                        fontFamily = FontFamily(Font(Res.font.Pretendard_Medium)),
                        fontWeight = FontWeight(700),
                        color = BeumColors.baseGrayLightGray600,
                        textAlign = TextAlign.Center,
                    )
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth().height(56.dp).background(color = BeumColors.White)
                    .padding(start = 20.dp, top = 0.dp, end = 20.dp, bottom = 0.dp), verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "이메일", style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 20.96.sp,
                        fontFamily = FontFamily(Font(Res.font.Pretendard_Medium)),
                        fontWeight = FontWeight(700),
                        color = BeumColors.baseGrayLightGray800,
                        textAlign = TextAlign.Center,
                    )
                )
                Spacer(modifier = Modifier.weight(1f))
                var resource = Res.drawable.kakao
                var color: Color
                when (info.socialType) {
                    SocialType.GOOGLE_CODE -> {
                        color = BeumColors.White
                        resource = Res.drawable.google
                    }

                    SocialType.APPLE_CODE -> {
                        color = BeumColors.Black
                        resource = Res.drawable.apple
                    }

                    else -> {
                        color = BeumColors.KaKaoColor
                        resource = Res.drawable.kakao
                    }
                }



                Box(
                    modifier = Modifier.size(20.dp)
                        .background(color, shape = RoundedCornerShape(size = 42.66667.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(resource),
                        contentDescription = "",
                        modifier = Modifier.size(12.dp)
                    )
                }

                Spacer(modifier = Modifier.width(5.dp))

                Text(
                    text = info.email, style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 20.96.sp,
                        fontFamily = FontFamily(Font(Res.font.Pretendard_Medium)),
                        fontWeight = FontWeight(700),
                        color = BeumColors.baseGrayLightGray600,
                        textAlign = TextAlign.Center,
                    )
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth().height(56.dp).background(color = BeumColors.White)
                    .padding(start = 20.dp, top = 0.dp, end = 20.dp, bottom = 0.dp).clickable {
                        action(MainAction.Withdraw)
                    }, verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "회원 탈퇴", style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 20.96.sp,
                        fontFamily = FontFamily(Font(Res.font.Pretendard_Medium)),
                        fontWeight = FontWeight(700),
                        color = BeumColors.baseGrayLightGray800,
                        textAlign = TextAlign.Center,
                    )
                )
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}