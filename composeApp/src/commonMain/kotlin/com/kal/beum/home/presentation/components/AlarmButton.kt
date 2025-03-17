package com.kal.beum.home.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color

@Composable
fun AlarmButton(modifier: Modifier = Modifier) {

    Box(modifier = modifier.background(Color.White).clip(RoundedCornerShape(100))) {
        Icon(imageVector = Icons.Default.Notifications, contentDescription = null)
    }

}