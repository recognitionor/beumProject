package com.kal.beum.content.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import beumproject.composeapp.generated.resources.Res
import beumproject.composeapp.generated.resources.ic_heart_empty
import beumproject.composeapp.generated.resources.ic_heart_filled
import com.kal.beum.home.presentation.components.Indicator
import org.jetbrains.compose.resources.painterResource

@Composable
fun LikeButton(
    isLike: Boolean,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    // 프레스 중에만 살짝 회색 틴트 + 살짝 축소
    val pressTint = if (isPressed) Color.Red else Color.Unspecified
    val scale = if (isPressed) 2f else 1f

    Image(
        modifier = Modifier
            .graphicsLayer(scaleX = scale, scaleY = scale) // 눌림 동안만 작게
            .clickable(
                interactionSource = interactionSource,
                indication = null // ✅ 기본 회색(사각형) ripple 제거
            ) { onClick() },
        painter = painterResource(
            if (isLike) Res.drawable.ic_heart_filled else Res.drawable.ic_heart_empty
        ),
        contentDescription = "",
        colorFilter = if (isPressed)ColorFilter.tint(pressTint, BlendMode.SrcIn) else null // 눌림 동안만 색
    )
}