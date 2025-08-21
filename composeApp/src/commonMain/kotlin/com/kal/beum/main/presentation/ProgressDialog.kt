package com.kal.beum.main.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import beumproject.composeapp.generated.resources.Res
import beumproject.composeapp.generated.resources.sf_pro
import com.kal.beum.core.presentation.BeumColors
import com.kal.beum.core.presentation.BeumDimen
import com.kal.beum.core.presentation.BeumTypo
import org.jetbrains.compose.resources.Font

/**
 * ProgressDialog
 *
 * @param message 진행 메시지
 * @param progress null이면 인디터미넌트, 0f..1f 주면 디터미넌트
 * @param cancellable 배경 탭으로 닫을 수 있는지
 * @param onDismiss 취소/닫기 콜백 (cancellable=true일 때만 사용됨)
 */
@Composable
fun ProgressDialog(
    message: String = "처리 중입니다...",
    progress: Float? = null,
    cancellable: Boolean = false,
    onDismiss: (() -> Unit)? = null,
) {
    Box(
        modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.3f)) // 배경 반투명 처리
            .clickable {
                if (onDismiss != null) {
                    onDismiss()
                }

            }, contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.width(300.dp).clip(RoundedCornerShape(12.dp))
                .background(Color.White)
                .padding(start = 16.dp, end = 16.dp, bottom = 20.dp, top = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = message)

        }
    }
}
