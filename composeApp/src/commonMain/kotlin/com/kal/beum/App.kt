@file:OptIn(ExperimentalMaterial3Api::class)

package com.kal.beum

import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.kal.beum.core.presentation.BeumColors
import com.kal.beum.main.presentation.MainScreen
import com.kal.beum.core.presentation.CommonBackHandler
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    val navController = rememberNavController()
    MaterialTheme {
        androidx.compose.material3.Surface( // 여기서 Surface 추가!
            modifier = Modifier.fillMaxSize(), color = BeumColors.baseCoolGrayLightGray100
        ) {
            val density = LocalDensity.current
            val edgeThresholdPx = with(density) { 50.dp.toPx() }
            var triggered by remember { mutableStateOf(false) }

            val onBack = {
                // 여기에 뒤로가기 시 수행할 공통 로직을 작성하세요.
                // 예: navController.popBackStack()
                println("Back action triggered (Android Back Key or iOS Swipe)")
            }

            CommonBackHandler(onBack = onBack)

            Box(modifier = Modifier.pointerInput(Unit) {
                detectHorizontalDragGestures(onDragStart = { offset ->
                    // 왼쪽 가장자리에서 시작한 경우에만 활성화
                    triggered = offset.x <= edgeThresholdPx
                }, onHorizontalDrag = { _, dragAmount ->
                    if (triggered && dragAmount > 100f) {
                        triggered = false // 한 번만 처리
                        onBack() // 스와이프 시에도 공통 백 액션 실행
                    }
                }, onDragEnd = {
                    triggered = false // 드래그 종료 시 초기화
                }, onDragCancel = {
                    triggered = false // 드래그 취소 시 초기화
                })
            }) {
                MainScreen(navController)
            }
        }
    }
}