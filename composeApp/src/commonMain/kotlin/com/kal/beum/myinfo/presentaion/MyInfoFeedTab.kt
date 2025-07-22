package com.kal.beum.myinfo.presentaion

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import beumproject.composeapp.generated.resources.Res
import beumproject.composeapp.generated.resources.ic_heart_empty
import beumproject.composeapp.generated.resources.ic_reply
import beumproject.composeapp.generated.resources.sf_pro
import com.kal.beum.core.presentation.BeumColors
import com.kal.beum.core.presentation.BeumDimen
import com.kal.beum.core.presentation.BeumTypo
import com.kal.beum.myinfo.domain.MyContent
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource

@Composable
fun MyInfoFeedTab(
    devil: Boolean,
    list: List<MyContent>,
    selectedTabIndex: Int,
    onItemClick: (MyContent) -> Unit,
    onTabSelected: (Int) -> Unit
) {
    val tabs = listOf("작성한 글", "댓글단 글")

    Column {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = Color.White,
            indicator = { tabPositions ->
                val tabPosition = tabPositions[selectedTabIndex]
                Box(
                    Modifier.tabIndicatorOffset(tabPosition).padding(horizontal = 50.dp) // 중앙정렬
                        .width(80.dp).height(3.dp)
                        .background(BeumColors.Black, RoundedCornerShape(2.dp))
                )
            }) {
            tabs.forEachIndexed { index, title ->
                Tab(text = {
                    Text(
                        text = title, style = TextStyle(
                            fontSize = 16.sp,
                            lineHeight = 20.96.sp,
                            fontFamily = FontFamily(Font(Res.font.sf_pro)),
                            fontWeight = FontWeight(700),
                            color = if (selectedTabIndex == index) BeumColors.Black else BeumColors.Gray_400,
                            textAlign = TextAlign.Center,
                        )
                    )
                }, selected = selectedTabIndex == index, onClick = {
                    println("index : $index")
                    onTabSelected.invoke(index)
                })
            }
        }

        Box(
            modifier = Modifier.fillMaxWidth().fillMaxHeight()
                .background(BeumColors.baseGrayLightGray75),
            contentAlignment = Alignment.Center,

            ) {

            if (list.isEmpty()) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = "작성한 글이 없어요.\n나의 고민을 등록해봐요.", style = TextStyle(
                            fontSize = 13.sp,
                            lineHeight = BeumTypo.TypoLienheigtLineheight300,
                            fontFamily = FontFamily(Font(Res.font.sf_pro)),
                            fontWeight = FontWeight(400),
                            color = BeumColors.baseGrayLightGray400,
                            textAlign = TextAlign.Center,
                        )
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "고민글 작성하기", style = TextStyle(
                            fontSize = BeumTypo.TypoScaleText150,
                            fontFamily = FontFamily(Font(Res.font.sf_pro)),
                            fontWeight = FontWeight(500),
                            color = BeumColors.baseGrayLightGray900,
                            textAlign = TextAlign.Center,
                        )
                    )
                }

            } else {
                Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                    Spacer(modifier = Modifier.height(20.dp))
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(16.dp) // 아이템 사이 16dp 간격
                    ) {
                        items(list.size) { index ->
                            val item = list[index]
                            Column(
                                modifier = Modifier.shadow(
                                    elevation = 20.dp,
                                    spotColor = Color(0x0D000000),
                                    ambientColor = Color(0x0D000000)
                                ).clickable {
                                    onItemClick.invoke(item)
                                }.fillMaxWidth().height(180.dp).background(
                                    color = BeumColors.White,
                                    shape = RoundedCornerShape(size = BeumDimen.radius100)
                                ).padding(start = 20.dp, end = 20.dp, top = 16.dp),
                            ) {
                                Box(
                                    modifier = Modifier.background(
                                        color = BeumColors.baseGrayLightGray75,
                                        shape = RoundedCornerShape(size = 4.dp)
                                    ).padding(start = 4.dp, top = 2.dp, end = 4.dp, bottom = 2.dp)
                                ) {
                                    Text(
                                        text = item.category, style = TextStyle(
                                            fontSize = 9.sp,
                                            lineHeight = BeumTypo.lineHeightCaption1,
                                            fontFamily = FontFamily(Font(Res.font.sf_pro)),
                                            fontWeight = FontWeight(400),
                                            color = BeumColors.baseGrayLightGray700,
                                        )
                                    )
                                }

                                Spacer(modifier = Modifier.height(10.dp))

                                Text(
                                    text = item.title, style = TextStyle(
                                        fontSize = 16.sp,
                                        lineHeight = BeumTypo.lineHeightBody3,
                                        fontFamily = FontFamily(Font(Res.font.sf_pro)),
                                        fontWeight = FontWeight(500),
                                        color = BeumColors.Black,
                                    )
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                Text(
                                    text = item.content, style = TextStyle(
                                        fontSize = 14.sp,
                                        lineHeight = 22.sp,
                                        fontFamily = FontFamily(Font(Res.font.sf_pro)),
                                        fontWeight = FontWeight(400),
                                        color = BeumColors.GrayGray500,
                                    )
                                )

                                Spacer(modifier = Modifier.weight(1f))

                                Row(
                                    modifier = Modifier.height(20.dp).fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Image(
                                        painter = painterResource(Res.drawable.ic_heart_empty),
                                        contentDescription = ""
                                    )
                                    Text(
                                        text = item.likeCount.toString(), style = TextStyle(
                                            fontSize = 13.sp,
                                            lineHeight = 20.sp,
                                            fontFamily = FontFamily(Font(Res.font.sf_pro)),
                                            fontWeight = FontWeight(400),
                                            color = BeumColors.GrayGray500,
                                        )
                                    )
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Image(
                                        painter = painterResource(Res.drawable.ic_reply),
                                        contentDescription = ""
                                    )
                                    Text(
                                        text = item.replyCount.toString(), style = TextStyle(
                                            fontSize = 13.sp,
                                            lineHeight = 20.sp,
                                            fontFamily = FontFamily(Font(Res.font.sf_pro)),
                                            fontWeight = FontWeight(400),
                                            color = BeumColors.GrayGray500,
                                        )
                                    )
                                    Spacer(modifier = Modifier.weight(1f))
                                }
                                Spacer(modifier = Modifier.height(16.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}