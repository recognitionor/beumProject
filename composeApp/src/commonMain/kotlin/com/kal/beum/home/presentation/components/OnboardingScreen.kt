package com.kal.beum.home.presentation.components

import org.jetbrains.compose.resources.painterResource
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import beumproject.composeapp.generated.resources.Res
import beumproject.composeapp.generated.resources.devil_abled
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Composable
fun OnboardingScreen(onFinish: () -> Unit) {

    val coroutineScope = rememberCoroutineScope()

    val pages = listOf(
        OnboardingPage(
            title = "비움에서 나의 고민을 털어놓아요",
            description = "비움은 서로의 고민을 털어놓고 위로와 공감을 해주는 커뮤니티에요",
            image = painterResource(Res.drawable.devil_abled)
        ), OnboardingPage(
            title = "따뜻한 공감과 위로가 필요할 땐",
            description = "속상한 일 때문에 위로받고 싶을 때는 천사 커뮤니티로 가요",
            image = painterResource(Res.drawable.devil_abled)
        ), OnboardingPage(
            title = "화나고 답답한 일이 있을 땐",
            description = "화나는 일 때문에 분노의 감정을 공유하려면 악마 커뮤니티로 가요",
            image = painterResource(Res.drawable.devil_abled)
        ), OnboardingPage(
            title = "랭킹으로 나의 비움 지수를 확인해요",
            description = "비움은 서로의 고민을 털어놓고 위로와 공감을 해주는 커뮤니티에요",
            image = painterResource(Res.drawable.devil_abled)
        )
    )

    val pagerState = rememberPagerState { pages.size } // ✅ 최신 API 적용

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        HorizontalPager(
            state = pagerState, modifier = Modifier.weight(1f)
        ) { page ->
            OnboardingPageContent(pages[page])
        }


        // ✅ 페이지 인디케이터 추가
        Row(
            modifier = Modifier.padding(16.dp), horizontalArrangement = Arrangement.Center
        ) {
            repeat(pages.size) { index ->
                Indicator(isSelected = index == pagerState.currentPage)
            }
        }

        Button(
            onClick = {
                coroutineScope.launch {
                    if (pagerState.currentPage == pages.size - 1) {
                        onFinish()
                    } else {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                }
            }, modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Text("다음")
        }

    }
}

@Composable
fun OnboardingPageContent(page: OnboardingPage) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = page.image, contentDescription = null, modifier = Modifier.size(80.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(page.title, fontSize = 20.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            page.description,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
        )
    }
}

@Composable
fun Indicator(isSelected: Boolean) {
    Box(
        modifier = Modifier.size(if (isSelected) 10.dp else 8.dp).padding(horizontal = 4.dp)
            .background(
                color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground.copy(
                    alpha = 0.3f
                ), shape = MaterialTheme.shapes.small
            )
    )
}

data class OnboardingPage(
    val title: String, val description: String, val image: Painter
)
