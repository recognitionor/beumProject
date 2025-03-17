package com.kal.beum.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
//
//@Composable
//fun pxToDp(pxValue: Float): Dp {
//    val density = LocalDensity.current.density
//    return (pxValue / density).dp
//}

@Composable
fun pxToDp(pxValue: Float): Dp {
    return with(LocalDensity.current) {
        pxValue.toDp()
    }
}