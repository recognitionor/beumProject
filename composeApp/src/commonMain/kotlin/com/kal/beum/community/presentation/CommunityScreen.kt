package com.kal.beum.community.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import beumproject.composeapp.generated.resources.Res
import beumproject.composeapp.generated.resources.ic_add_medium
import beumproject.composeapp.generated.resources.sf_pro
import com.kal.beum.core.presentation.BeumColors
import com.kal.beum.core.presentation.BeumTypo
import com.kal.beum.home.presentation.components.ToggleButton
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun CommunityScreen(isDevil: Boolean, toggleClicked: (isDevil: Boolean) -> Unit) {
    val viewModel = koinViewModel<CommunityViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()

    LaunchedEffect(Unit) {
        viewModel.getCategory(isDevil)
    }

    // 👇 선택된 카테고리가 바뀔 때마다 맨 위로 스크롤
    LaunchedEffect(state.selectedCategoryId) {
        listState.scrollToItem(0)
    }

    Column {
        Box(
            modifier = Modifier.fillMaxWidth().height(64.dp).padding(start = 26.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            ToggleButton(toggle = isDevil, toggleClicked = toggleClicked)
        }

        Spacer(modifier = Modifier.height(12.dp))

        LazyRow(modifier = Modifier.height(52.dp), verticalAlignment = Alignment.CenterVertically) {
            items(state.categoryList.size) {

                Text(
                    modifier = Modifier.padding(start = 22.dp, end = 22.dp).clickable {
                        viewModel.onAction(CommunityAction.OnTabSelected(it, isDevil))
                    }, text = state.categoryList[it].category, style = TextStyle(
                        fontSize = BeumTypo.TypoScaleText150,
                        lineHeight = 20.96.sp,
                        fontFamily = FontFamily(Font(Res.font.sf_pro)),
                        fontWeight = FontWeight(700),
                        color = if (state.selectedCategoryId == it) BeumColors.Black else BeumColors.baseGrayLightGray300,
                        textAlign = TextAlign.Center,
                    )
                )

            }
        }
        if (state.communityList.isNotEmpty()) {
            val communityList = state.communityList[state.selectedCategoryId]
            Box {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(), state = listState
                ) {
                    items(communityList.size) {
                        val item = communityList[it]
                        Column(
                            modifier = Modifier.height(130.dp).fillMaxWidth()
                                .padding(start = 20.dp, end = 20.dp, top = 16.dp, bottom = 24.dp)
                        ) {
                            Row(modifier = Modifier.fillMaxWidth().height(20.dp)) {
                                if (item.isPopular) {
                                    TagCard(
                                        color = Color(0xFFF1E6FF),
                                        textColor = Color(0xFFA65DFF),
                                        text = "인기"
                                    )
                                }

                                Spacer(modifier = Modifier.width(4.dp))

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
                                    color = BeumColors.Black,
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
                    }
                }

                Box(
                    modifier = Modifier.padding(12.dp).align(Alignment.BottomEnd)
                ) {
                    Box(
                        modifier = Modifier.width(52.dp).height(52.dp).background(
                            color = BeumColors.primaryPrimarySkyblue,
                            shape = RoundedCornerShape(size = 100.dp)
                        ).clickable {
                            //
                        }
                    ) {
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