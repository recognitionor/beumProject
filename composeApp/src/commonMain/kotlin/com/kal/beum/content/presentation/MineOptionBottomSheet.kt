package com.kal.beum.content.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kal.beum.core.presentation.BeumColors


@Composable
fun MineOptionBottomSheet(
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            .padding(bottom = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(12.dp))
        Box(
            modifier = Modifier
                .height(4.dp)
                .fillMaxWidth(0.1f)
                .clip(RoundedCornerShape(2.dp))
                .background(Color.Gray)
        )
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "글 관리",
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "수정하기",
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onEdit() }
                .padding(vertical = 16.dp),
            textAlign = TextAlign.Center,
            style = TextStyle(fontSize = 16.sp, color = Color.Black)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(BeumColors.baseGrayLightGray100)
        )

        Text(
            text = "삭제하기",
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onDelete() }
                .padding(vertical = 16.dp),
            textAlign = TextAlign.Center,
            style = TextStyle(fontSize = 16.sp, color = Color.Red)
        )
    }
}
