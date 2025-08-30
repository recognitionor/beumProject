package com.kal.beum.community.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kal.beum.write.domain.WritingCategory

@Composable
fun DraftDialog(
    onNewClick: () -> Unit, onContinueClick: () -> Unit, onDismiss: (selectItem: WritingCategory?) -> Unit
) {
    // 다이얼로그 바깥쪽을 누르면 닫히도록 Box로 감싸기
    Box(
        modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.3f)) // 배경 반투명 처리
            .clickable { onDismiss(null) }, contentAlignment = Alignment.Center
    ) {
        // 실제 다이얼로그 박스
        Column(
            modifier = Modifier.width(300.dp).clip(RoundedCornerShape(12.dp)).background(Color.White)
                .padding(start = 16.dp, end = 16.dp, bottom = 20.dp, top = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 제목
            Text(
                text = "작성 중인 글이 있어요", style = TextStyle(
                    fontSize = 17.sp, fontWeight = FontWeight.Bold, color = Color.Black
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 버튼 영역
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // 새로쓰기 버튼
                Box(
                    modifier = Modifier.weight(1f).height(48.dp).clip(RoundedCornerShape(6.dp))
                        .background(Color(0xFFF2F2F2)).clickable {
                            onNewClick()
                        }, contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "새로 쓰기", style = TextStyle(
                            fontSize = 15.sp, fontWeight = FontWeight.Medium, color = Color.Black
                        )
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                // 이어서 쓰기 버튼
                Box(
                    modifier = Modifier.weight(1f).height(48.dp).clip(RoundedCornerShape(6.dp))
                        .background(Color(0xFF00BFFF)) // 밝은 파란색
                        .clickable { onContinueClick() }, contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "이어서 쓰기", style = TextStyle(
                            fontSize = 15.sp, fontWeight = FontWeight.Medium, color = Color.White
                        )
                    )
                }
            }
        }
    }
}
