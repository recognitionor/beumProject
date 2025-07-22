package com.kal.beum.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import kotlinx.datetime.Clock
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.DurationUnit

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

fun formatWithComma(number: Int): String {
    val s = number.toString()
    val sb = StringBuilder()
    var count = 0
    for (i in s.length - 1 downTo 0) {
        sb.append(s[i])
        count++
        if (count % 3 == 0 && i != 0) {
            sb.append(',')
        }
    }
    return sb.reverse().toString()
}

fun formatTimeAgo(timestampMillis: Long): String {
    val now = Clock.System.now().toEpochMilliseconds()
    val diffMillis = now - timestampMillis
    val duration = diffMillis.milliseconds

    val days = duration.toInt(DurationUnit.DAYS)
    return if (days >= 1) {
        "${days}일 전"
    } else {
        val hours = duration.toInt(DurationUnit.HOURS)
        "${hours}시간 전"
    }
}