package com.kal.beum.write.presentation// DynamicInfoBottomSheet.kt (새 파일 또는 com.kal.beum.write.presentation.InfoBottomSheet 정의 옆에 추가)

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kal.beum.core.presentation.BeumColors

@Composable
fun InfoBottomSheet(type: InfoBottomSheetType, onConfirm: () -> Unit) {
    // InfoBottomSheetType.None 일 경우 호출되면 안 되므로 여기서 다시 확인
    if (type == InfoBottomSheetType.None) return

    Column(
        modifier = Modifier.fillMaxWidth().background(Color.White)
            .padding(horizontal = 20.dp, vertical = 24.dp), horizontalAlignment = Alignment.Start
    ) {
        val title = when (type) {
            is InfoBottomSheetType.TagInfo -> type.title
            is InfoBottomSheetType.CommunityInfo -> type.title
            is InfoBottomSheetType.PointInfo -> type.title
            is InfoBottomSheetType.GuideInfo -> type.title
            else -> "" // Fallback, shouldn't happen if None is handled
        }
        val bullets = when (type) {
            is InfoBottomSheetType.TagInfo -> type.bullets
            is InfoBottomSheetType.CommunityInfo -> type.bullets
            is InfoBottomSheetType.PointInfo -> type.bullets
            is InfoBottomSheetType.GuideInfo -> type.bullets
            else -> emptyList() // Fallback
        }

        Text(
            text = title, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black
        )

        Spacer(modifier = Modifier.height(16.dp))

        bullets.forEach { bullet ->
            Row(verticalAlignment = Alignment.Top) {
                Text(
                    text = "• ", fontSize = 16.sp, color = Color.Black
                )
                Text(
                    text = bullet, fontSize = 16.sp, color = Color.DarkGray
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { onConfirm() },
            modifier = Modifier.fillMaxWidth().height(52.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = BeumColors.angelSkyblue, contentColor = Color.White
            )
        ) {
            Text(
                text = "확인", fontSize = 18.sp, fontWeight = FontWeight.Bold
            )
        }
    }
}