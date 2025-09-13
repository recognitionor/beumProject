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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.kal.beum.main.presentation.FullScreenType
import com.kal.beum.main.presentation.MainAction
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource

@Composable
fun ServicePolicyInfoScreen(action: (MainAction) -> Unit) {

    Column(Modifier.fillMaxSize().background(BeumColors.baseGrayLightGray75)) {
        Column(
            modifier = Modifier.background(
                BeumColors.White
            )
        ) {
            Spacer(modifier = Modifier.height(50.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically, modifier = Modifier.background(
                    BeumColors.White
                ).fillMaxWidth()
            ) {
                Box(modifier = Modifier.clickable {
                    action(MainAction.PopFullScreen)
                }.size(48.dp), contentAlignment = Alignment.Center) {
                    Image(
                        painter = painterResource(Res.drawable.icon_arrow_right_black),
                        contentDescription = "",
                        modifier = Modifier.graphicsLayer {
                            scaleX = -1f // 좌우 반전!
                        }.size(24.dp)
                    )
                }

                Text(
                    text = "서비스 정보 및 약관", style = TextStyle(
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

            Row(
                modifier = Modifier.clickable {
                    action(MainAction.PushFullScreen(FullScreenType.PrivacyPolicyScreen))
                }.fillMaxWidth().height(56.dp).background(color = BeumColors.White)
                    .padding(start = 20.dp, top = 0.dp, end = 20.dp, bottom = 0.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "개인 정보 처리 방침", style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 20.96.sp,
                        fontFamily = FontFamily(Font(Res.font.Pretendard_Medium)),
                        fontWeight = FontWeight(700),
                        color = BeumColors.baseGrayLightGray800,
                        textAlign = TextAlign.Center,
                    )
                )
                Spacer(modifier = Modifier.weight(1f))

                Box(modifier = Modifier.size(48.dp), contentAlignment = Alignment.Center) {
                    Image(
                        painter = painterResource(Res.drawable.icon_arrow_right_black),
                        contentDescription = ""
                    )
                }
            }

            Row(
                modifier = Modifier.clickable {
                    action(MainAction.PushFullScreen(FullScreenType.TermScreen));
                }.fillMaxWidth().height(56.dp).background(color = BeumColors.White)
                    .padding(start = 20.dp, top = 0.dp, end = 20.dp, bottom = 0.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "이용 약관", style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 20.96.sp,
                        fontFamily = FontFamily(Font(Res.font.Pretendard_Medium)),
                        fontWeight = FontWeight(700),
                        color = BeumColors.baseGrayLightGray800,
                        textAlign = TextAlign.Center,
                    )
                )
                Spacer(modifier = Modifier.weight(1f))

                Box(modifier = Modifier.size(48.dp), contentAlignment = Alignment.Center) {
                    Image(
                        painter = painterResource(Res.drawable.icon_arrow_right_black),
                        contentDescription = ""
                    )
                }
            }
        }
    }
}