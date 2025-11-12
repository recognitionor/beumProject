package com.kal.beum.community.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import beumproject.composeapp.generated.resources.Res
import beumproject.composeapp.generated.resources.ic_add_medium
import beumproject.composeapp.generated.resources.sf_pro
import com.kal.beum.community.domain.Category
import com.kal.beum.core.presentation.BeumColors
import com.kal.beum.core.presentation.BeumTypo
import com.kal.beum.home.presentation.components.ToggleButton
import com.kal.beum.main.presentation.FullScreenType
import com.kal.beum.main.presentation.MainAction
import kotlinx.coroutines.flow.distinctUntilChanged
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource


@Composable
fun CommunityScreen(
    isDevil: Boolean, viewModel: CommunityViewModel, onAction: (MainAction) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()

    LaunchedEffect(isDevil) {
        viewModel.getCategory(isDevil)
    }

    // 👇 선택된 카테고리가 바뀔 때마다 맨 위로 스크롤
    LaunchedEffect(state.selectedCategoryId) {
        listState.scrollToItem(0)
    }

    if (state.onProgress) {
        onAction(MainAction.PushFullScreen(FullScreenType.ProgressDialog))
    } else {
        onAction(MainAction.CloseFullScreen(FullScreenType.ProgressDialog))
    }
    onAction(MainAction.SurfaceColor(if (isDevil) BeumColors.DarkGray50 else BeumColors.White))
    Column {
        Box(
            modifier = Modifier.fillMaxWidth().height(64.dp).padding(start = 26.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            ToggleButton(toggle = isDevil, if (isDevil) Color.Black else BeumColors.baseCoolGrayLightGray100) {
                onAction(MainAction.ToggleDevil(it))
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        LazyRow(modifier = Modifier.height(52.dp), verticalAlignment = Alignment.CenterVertically) {
            items(state.categoryList.size) {
                val textColor = if (isDevil) {
                    if (state.selectedCategoryId == it) BeumColors.White else BeumColors.baseGrayLightGray600
                } else {
                    if (state.selectedCategoryId == it) BeumColors.Black else BeumColors.baseGrayLightGray300
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillParentMaxHeight()
                        // .wrapContentWidth() // <-- 이 부분을
                        .width(IntrinsicSize.Max) // <-- 이렇게 변경합니다.
                        .clip(RoundedCornerShape(12.dp)) // 클릭과 패딩은 Column에 두는 것이 맞습니다.
                        .clickable(
                            indication = null,
                            interactionSource = androidx.compose.foundation.interaction.MutableInteractionSource()
                        ) {
                            viewModel.onAction(CommunityAction.OnTabSelected(it, isDevil))
                        }
                        .padding(start = 22.dp, end = 22.dp), // IntrinsicSize 계산 후 패딩 적용
                    verticalArrangement = Arrangement.Center
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        modifier = Modifier, // 여기는 수정할 필요 없습니다.
                        text = state.categoryList[it].category,
                        style = TextStyle(
                            fontSize = BeumTypo.TypoScaleText150,
                            lineHeight = 20.96.sp,
                            fontFamily = FontFamily(Font(Res.font.sf_pro)),
                            fontWeight = FontWeight(700),
                            color = textColor,
                            textAlign = TextAlign.Center,
                        )
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    if (state.selectedCategoryId == it) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth() // 이제 Column의 'IntrinsicSize.Max' 너비를 채웁니다.
                                .height(3.14.dp)
                                .background(if (isDevil) Color.White else Color.Black)
                        )
                    }
                }
            }
        }
        if (state.communityListTemp.isNotEmpty()) {
            val communityList = state.communityListTemp
            Box(modifier = Modifier.background(if (isDevil) BeumColors.DarkGray50 else BeumColors.baseGrayLightGray75)) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                        .padding(start = 20.dp, end = 20.dp, top = 16.dp, bottom = 24.dp),
                    state = listState
                ) {
                    items(communityList.size) {
                        val item = communityList[it]
                        Column(
                            modifier = Modifier.shadow(
                                elevation = 8.dp,
                                spotColor = Color(0x0D000000),
                                ambientColor = Color(0x0D000000)
                            ).clip(RoundedCornerShape(9.dp)).clickable {
                                onAction(
                                    MainAction.PushFullScreen(
                                        FullScreenType.ContentDetailScreen(
                                            item.id
                                        )
                                    )
                                )
                            }.background(
                                color = if (isDevil) BeumColors.baseGrayLightGray800 else BeumColors.baseGrayLightGray50,
                                shape = RoundedCornerShape(9.dp)
                            ).padding(start = 20.dp, top = 16.dp, end = 20.dp, bottom = 24.dp)
                                .fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth().height(20.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                if (item.isPopular) {
                                    TagCard(
                                        color = Color(0xFFF1E6FF),
                                        textColor = Color(0xFFA65DFF),
                                        text = "인기"
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                }

                                TagCard(
                                    color = BeumColors.baseGrayLightGray75,
                                    textColor = BeumColors.baseGrayLightGray700,
                                    text = item.categoryName
                                )
                            }
                            Text(
                                text = item.title, style = TextStyle(
                                    fontSize = 16.sp,
                                    lineHeight = 18.sp,
                                    fontFamily = FontFamily(Font(Res.font.sf_pro)),
                                    fontWeight = FontWeight(500),
                                    color = if (isDevil) BeumColors.White else BeumColors.Black,
                                )
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = item.content, style = TextStyle(
                                    fontSize = 13.sp,
                                    lineHeight = 16.sp,
                                    fontFamily = FontFamily(Font(Res.font.sf_pro)),
                                    fontWeight = FontWeight(400),
                                    color = BeumColors.GrayGray500,
                                )
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }

                Box(
                    modifier = Modifier.padding(12.dp).align(Alignment.BottomEnd)
                ) {
                    Box(
                        modifier = Modifier.width(52.dp).height(52.dp).background(
                            color = BeumColors.angelSkyblue,
                            shape = RoundedCornerShape(size = 100.dp)
                        ).clip(shape = RoundedCornerShape(size = 100.dp)).clickable {
                            println("close")
                            onAction(MainAction.NewWriting {
                                val tempCategory =
                                    state.categoryList.find { it.id == state.selectedCategoryId }
                                        ?: Category(-1, "전체")
                                viewModel.getItemsByCategoryTemp(tempCategory, isDevil)
                            })
                        }) {
                        Image(
                            painter = painterResource(Res.drawable.ic_add_medium),
                            modifier = Modifier.align(Alignment.Center),
                            contentDescription = ""
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TagCard(color: Color, textColor: Color, text: String) {
    Text(
        modifier = Modifier.height(20.dp).background(
            color = color, shape = RoundedCornerShape(size = 4.dp)
        ).padding(
            start = 4.dp, top = 2.dp, end = 4.dp, bottom = 2.dp
        ), text = text, style = TextStyle(
            fontSize = 9.sp,
            lineHeight = 16.sp,
            fontFamily = FontFamily(Font(Res.font.sf_pro)),
            fontWeight = FontWeight(400),
            color = textColor,
        )
    )
}