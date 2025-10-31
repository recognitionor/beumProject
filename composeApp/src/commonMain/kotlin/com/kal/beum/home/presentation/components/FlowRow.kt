package com.kal.beum.home.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import beumproject.composeapp.generated.resources.Res
import beumproject.composeapp.generated.resources.sf_pro
import com.kal.beum.core.presentation.BeumColors
import com.kal.beum.core.presentation.BeumTypo
import com.kal.beum.home.presentation.HomeViewModel
import com.kal.beum.utils.pxToDp
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import org.jetbrains.compose.resources.Font


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FlowRow(
    isDevil: Boolean, viewModel: HomeViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val repeatedList = state.homeCommentList
    repeatedList.forEach {
        println(it)
    }
    MarqueeLazyRowStyled(isDevil, repeatedList.map { it.concernMsg }) {
        viewModel.updateHomeCommentList()
    }
}

@Composable
fun MarqueeLazyRowStyled(isDevil: Boolean, texts: List<String>, loadMore: () -> Unit) {
    if (texts.isEmpty()) return

    val repeatedTexts = remember { texts }
    val listState = rememberLazyListState()

    val fullContentWidth = remember { mutableStateOf(0f) }
    val viewportWidth = remember { mutableStateOf(0f) }

    // 자동 스크롤 활성화 여부
    var autoScrollEnabled by remember { mutableStateOf(true) }

    // 사용자가 스크롤 중이면 자동 스크롤 멈춤
    LaunchedEffect(listState.isScrollInProgress) {
        if (listState.isScrollInProgress) {
            autoScrollEnabled = false
        } else {
            delay(1000) // 손 떼고 1초 후 자동 스크롤 재개
            autoScrollEnabled = true
        }
    }

    var isLoading by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        snapshotFlow {
            val ratio =
                listState.firstVisibleItemScrollOffset.toFloat() / (fullContentWidth.value - viewportWidth.value).coerceAtLeast(
                    1f
                )
            ratio
        }.map { (it * 100).toInt() / 5 * 5 } // 5% 단위로 그룹핑
            .distinctUntilChanged().filter { it >= 95 } // 95% 이상일 때만
            .collect {
                loadMore()
                if (!isLoading) {
                    isLoading = true
                    println("스크롤 비율: $it%")
                    loadMore()
                    // 가짜 로딩 딜레이 예시 — 실제 로직 완료 후 false 처리
                    delay(2000)
                    isLoading = false
                }
            }
    }

    // 자동 스크롤
    LaunchedEffect(autoScrollEnabled) {
        while (autoScrollEnabled) {
            listState.scrollBy(3f)
            delay(16L)
        }
    }
    Box(modifier = Modifier.onGloballyPositioned {
        viewportWidth.value = it.size.width.toFloat()
    }) {
        LazyRow(
            state = listState,
            modifier = Modifier.fillMaxWidth().height(200.dp),
            userScrollEnabled = true, // 손가락 스크롤 방지
            verticalAlignment = Alignment.Top,
        ) {
            // 두 줄로 나누기
            val line1 = repeatedTexts.filterIndexed { i, _ -> i % 2 == 0 }
            val line2 = repeatedTexts.filterIndexed { i, _ -> i % 2 == 1 }

            item {
                Column(modifier = Modifier.onGloballyPositioned {
                    fullContentWidth.value = it.size.width.toFloat()
                }) {
                    MarqueeStyledRow(line1, isDevil)
                    Spacer(modifier = Modifier.height(10.dp))
                    MarqueeStyledRow(line2, isDevil)
                }
            }
        }
    }
}

@Composable
private fun MarqueeStyledRow(texts: List<String>, isDevil: Boolean) {
    val devilColor = Brush.linearGradient(
        colors = listOf(
            Color(0xFF393E44), Color(0xFF2B3036)
        ), start = Offset(0f, 0f),          // 좌상단
        end = Offset(1000f, 1000f)       // 우하단 방향
    )

    val devilBorderColor = Brush.linearGradient(
        colors = listOf(
            Color(0xFF696C70), Color(0xFF33383E),Color(0xFF2B3036)
        ), start = Offset(0f, 0f),          // 좌상단
        end = Offset(1000f, 1000f)       // 우하단 방향
    )

    val angelColor = Brush.linearGradient(
        colors = listOf(
            Color(0xFFFFFFFF), Color(0xFFFFFFFF), Color(0xFFFFFFFF)
        ),
        start = Offset(0f, 0f),
        end = Offset(1000f, 1000f)
    )

    val angelBorderColor = Brush.linearGradient(
        colors = listOf(
            Color(0xFFFFFFFF), Color(0xFFF2EFED), Color(0xFFF3F3F3)
        ), start = Offset(0f, 0f),          // 좌상단
        end = Offset(1000f, 1000f)       // 우하단 방향
    )

    Row {
        texts.forEach { msg ->
            Box(
                modifier = Modifier.height(40.dp).clip(RoundedCornerShape(pxToDp(100f))).border(
                    width = 1.dp,
                    brush = if (isDevil) devilBorderColor else angelBorderColor,
                    shape = RoundedCornerShape(pxToDp(100f))
                ).background(brush = if (isDevil) devilColor else angelColor)
                    .padding(end = 16.dp, bottom = 2.dp, start = 16.dp)
                    .wrapContentWidth(Alignment.CenterHorizontally),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = msg,
                    maxLines = 1,
                    fontSize = 14.sp,
                    style = TextStyle(
                        fontSize = BeumTypo.TypoScaleText150,
                        lineHeight = BeumTypo.TypoLienheigtLineheight100,
                        fontFamily = FontFamily(Font(Res.font.sf_pro)),
                        fontWeight = FontWeight(500),
                        color = if (isDevil) BeumColors.baseAlphaWhiteLightWhite700A else BeumColors.baseGrayLightGray600
                    ),
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                        .widthIn(min = 50.dp, max = 240.dp)
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
        }
    }
}
