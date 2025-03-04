package com.kal.beum.home.presentation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import beumproject.composeapp.generated.resources.Res
import beumproject.composeapp.generated.resources.angel_abled
import beumproject.composeapp.generated.resources.angel_disabled
import beumproject.composeapp.generated.resources.compose_multiplatform
import beumproject.composeapp.generated.resources.devil_abled
import beumproject.composeapp.generated.resources.test
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.painterResource

@Composable
fun HomeScreen() {

    Column {
        Image(painter = painterResource(Res.drawable.devil_abled), contentDescription = null)
    }
}