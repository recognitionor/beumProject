import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kal.beum.home.domain.HomeData
import com.kal.beum.home.presentation.HomeViewModel
import com.kal.beum.utils.pxToDp
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FlowRowTest(viewModel: HomeViewModel, modifier: Modifier = Modifier) {

    val state by viewModel.state.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()

    LaunchedEffect(scrollState) {
        // 🚀 1. 마지막 아이템 도착 감지 (무한 스크롤)
        launch {
            snapshotFlow { scrollState.value }.distinctUntilChanged().collectLatest { scrollX ->
                if (scrollX >= scrollState.maxValue) { // 끝까지 스크롤 됐는지 확인
                    delay(500)
                    scrollState.scrollTo(0)
                    delay(500)
                }
            }
        }

        // 🚀 2. 스크롤 멈춤 감지
        launch {
            snapshotFlow { scrollState.isScrollInProgress }.distinctUntilChanged()
                .filter { isScrolling -> !isScrolling } // 스크롤이 멈춘 순간
                .collectLatest { isScrolling ->
                    if (!isScrolling) { // 스크롤이 멈춘 순간
                        launch {
                            scrollState.animateScrollBy(30f) // 자동 스크롤 실행
                        }
                    }
                }
        }
    }
    Column(
        modifier = Modifier.fillMaxSize().horizontalScroll(scrollState)
    ) {
        val halfSize = (state.homeCommentList.size + 1) / 2 // 아이템 개수를 반으로 나누기

        // 🔥 첫 번째 줄 (상위 FlowRow)
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(pxToDp(16f)),
            verticalArrangement = Arrangement.Center // 위쪽 정렬,
        ) {
            state.homeCommentList.take(halfSize).forEach { item -> // 첫 번째 줄 아이템
                FlowBox(item)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 🔥 두 번째 줄 (하위 FlowRow)
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(pxToDp(16f)),
            verticalArrangement = Arrangement.Top // 위쪽 정렬
        ) {
            state.homeCommentList.drop(halfSize).forEach { item -> // 두 번째 줄 아이템
                FlowBox(item)
            }
        }
    }
}

@Composable
fun FlowBox(item: HomeData) {
    Box(
        modifier = Modifier.clip(RoundedCornerShape(pxToDp(100f))).background(Color.White)
            .height(40.dp).padding(end = 16.dp, bottom = 2.dp, start = 16.dp) // 간격 최소화
            .wrapContentWidth(Alignment.CenterHorizontally), contentAlignment = Alignment.Center
    ) {
        Text(
            text = item.concernMsg, style = TextStyle(
                fontWeight = FontWeight.Medium, // ✅ 500 (Medium)
                lineHeight = 19.sp, // ✅ 줄 높이 (1.0x 비율)
                letterSpacing = 0.sp // ✅ 글자 간격 0
            )
        )
    }
}
