@file:OptIn(ExperimentalMaterial3Api::class)

package com.kal.beum

import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.kal.beum.core.presentation.BeumColors
import com.kal.beum.core.presentation.Toast
import com.kal.beum.core.presentation.ToastInfo
import com.kal.beum.main.presentation.MainAction
import com.kal.beum.main.presentation.MainScreen
import com.kal.beum.main.presentation.MainViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App() {
    val viewModel = koinViewModel<MainViewModel>()
    val navController = rememberNavController()
    MaterialTheme {
        androidx.compose.material3.Surface( // 여기서 Surface 추가!
            modifier = Modifier.fillMaxSize(), color = BeumColors.baseCoolGrayLightGray100
        ) {
            val density = LocalDensity.current
            val edgeThresholdPx = with(density) { 50.dp.toPx() }
            var triggered by remember { mutableStateOf(false) }

            val onBack = {
                println("onBack!!!!")
                viewModel.onBackKey()
            }
            PlatformBackHandler(true, onBack)
            Box(modifier = Modifier.pointerInput(Unit) {
                awaitPointerEventScope {
                    while (true) {
                        val down = awaitFirstDown(pass = PointerEventPass.Initial)
                        val startX = down.position.x
                        val edgeThreshold = 50.dp.toPx()

                        if (startX <= edgeThreshold) {
                            var triggered = false
                            var dragAmount = 0f
                            val pointerId = down.id

                            while (true) {
                                val event = awaitPointerEvent(pass = PointerEventPass.Initial)
                                val change = event.changes.find { it.id == pointerId }

                                if (change == null || !change.pressed) {
                                    break
                                }

                                val delta = change.positionChange().x
                                dragAmount += delta

                                if (!triggered) {
                                    if (dragAmount > 50.dp.toPx()) { // Trigger threshold
                                        onBack()
                                        triggered = true
                                        change.consume()
                                    }
                                } else {
                                    change.consume()
                                }
                            }
                        }
                    }
                }
            }) {


                MainScreen(navController, viewModel)
                viewModel.state.value.showToast?.let {
                    Toast(it) {
                        viewModel.onAction(MainAction.ToastMessage())
                    }
                }
            }
        }
    }
}