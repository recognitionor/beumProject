package com.kal.beum.content.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import beumproject.composeapp.generated.resources.Res
import beumproject.composeapp.generated.resources.sf_pro
import com.kal.beum.core.presentation.BeumColors
import org.jetbrains.compose.resources.Font

@Composable
fun ReplyOptionBottomSheet(canSelecting: Boolean, onReport: () -> Unit, onAdopt: () -> Unit) {
    Column(modifier = Modifier.padding(horizontal = 20.dp)) {
        Spacer(modifier = Modifier.height(10.dp))


        // 신고하기 버튼
        Box(
            modifier = Modifier.fillMaxWidth().height(50.dp).clickable {
                onReport()
            }, contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = "신고하기", style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(Res.font.sf_pro)),
                    fontWeight = FontWeight(500),
                    color = BeumColors.baseGrayLightGray800
                )
            )
        }
        // 구분선 (선택사항, 깔끔함을 위해 제외하거나 얇게 추가 가능)
        Box(
            modifier = Modifier.fillMaxWidth().height(1.dp)
                .background(BeumColors.baseGrayLightGray100)
        )
        if (canSelecting) {
            // 채택하기 버튼
            Box(
                modifier = Modifier.fillMaxWidth().height(50.dp).clickable {
                    onAdopt()
                }, contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = "채택하기", style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(Res.font.sf_pro)),
                        fontWeight = FontWeight(500),
                        color = BeumColors.angelSkyblue // 채택은 강조색 사용 추천
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}
