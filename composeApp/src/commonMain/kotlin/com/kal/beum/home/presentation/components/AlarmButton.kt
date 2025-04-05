package com.kal.beum.home.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import beumproject.composeapp.generated.resources.Res
import beumproject.composeapp.generated.resources.alarm
import org.jetbrains.compose.resources.painterResource

@Composable
fun AlarmButton(modifier: Modifier = Modifier) {

    Box(
        modifier = Modifier.width(48.dp).height(48.dp)
            .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 100.dp))
            .padding(start = 8.dp, top = 8.dp, end = 8.dp, bottom = 8.dp),
        contentAlignment = Alignment.Center // 가운데 정렬 (좌우 + 상하)
    ) {
        Icon(painter = painterResource(Res.drawable.alarm), contentDescription = null)
    }

}