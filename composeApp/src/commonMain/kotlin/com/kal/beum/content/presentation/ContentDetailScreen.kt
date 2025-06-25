package com.kal.beum.content.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import beumproject.composeapp.generated.resources.Res
import beumproject.composeapp.generated.resources.ic_angel_emoji
import beumproject.composeapp.generated.resources.ic_heart_empty
import beumproject.composeapp.generated.resources.ic_more_vertical_medium
import beumproject.composeapp.generated.resources.ic_reply
import beumproject.composeapp.generated.resources.icon_arrow_right_black
import beumproject.composeapp.generated.resources.sf_pro
import com.kal.beum.community.domain.CommunityItem
import com.kal.beum.core.presentation.BeumColors
import com.kal.beum.core.presentation.BeumTypo
import com.kal.beum.utils.formatTimeAgo
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource

@Composable
fun ContentDetailScreen(item: CommunityItem, backBtnClick: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize().background(Color.White)
            .padding(top = WindowInsets.systemBars.asPaddingValues().calculateTopPadding())
    ) {
        Column(modifier = Modifier.matchParentSize()) {
            Row(modifier = Modifier.padding(start = 20.dp, end = 20.dp)) {
                Box(modifier = Modifier.width(24.dp).height(24.dp).clickable {
                    backBtnClick()
                }) {
                    Image(
                        modifier = Modifier.size(24.dp).scale(scaleX = -1f, scaleY = 1f),
                        painter = painterResource(Res.drawable.icon_arrow_right_black),
                        contentDescription = ""
                    )
                }

                Spacer(modifier = Modifier.weight(1f))
                Image(
                    painter = painterResource(Res.drawable.ic_more_vertical_medium),
                    contentDescription = ""
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Box(
                modifier = Modifier.fillMaxWidth().height(1.dp)
                    .background(BeumColors.baseGrayLightGray100).padding(horizontal = 20.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                Row(modifier = Modifier.wrapContentHeight().fillMaxWidth()) {
                    Box(
                        modifier = Modifier.border(
                            width = 0.625.dp,
                            color = BeumColors.baseAlphaWhiteLightWhite500A,
                            shape = RoundedCornerShape(size = 20.dp)
                        ).width(40.dp).height(40.dp).background(
                            color = Color(0xFF45CAF7), shape = RoundedCornerShape(size = 20.dp)
                        ).padding(
                            start = 8.125.dp, top = 6.875.dp, end = 8.125.dp, bottom = 6.875.dp
                        )
                    ) {
                        Image(
                            painter = painterResource(Res.drawable.ic_angel_emoji),
                            contentDescription = "",
                            modifier = Modifier.size(40.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = item.writer, style = TextStyle(
                                fontSize = BeumTypo.TypoScaleText150,
                                fontFamily = FontFamily(Font(Res.font.sf_pro)),
                                fontWeight = FontWeight(600),
                                color = BeumColors.baseGrayLightGray800,
                            )
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = formatTimeAgo(item.lastModifiedTime), style = TextStyle(
                                fontSize = BeumTypo.TypoScaleText75,
                                fontFamily = FontFamily(Font(Res.font.sf_pro)),
                                fontWeight = FontWeight(400),
                                color = BeumColors.baseGrayLightGray500,
                            )
                        )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp).fillMaxWidth())

                Box(
                    modifier = Modifier.width(44.dp).height(20.dp).background(
                        color = BeumColors.baseGrayLightGray75,
                        shape = RoundedCornerShape(size = 4.dp)
                    ).padding(start = 4.dp, top = 2.dp, end = 4.dp, bottom = 2.dp)
                ) {
                    Text(
                        text = item.categoryName, style = TextStyle(
                            fontSize = 9.sp,
                            lineHeight = BeumTypo.lineHeightCaption1,
                            fontFamily = FontFamily(Font(Res.font.sf_pro)),
                            fontWeight = FontWeight(400),
                            color = BeumColors.baseGrayLightGray700,
                        )
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = item.title, style = TextStyle(
                        fontSize = BeumTypo.TypoScaleText400,
                        lineHeight = BeumTypo.TypoScaleText400,
                        fontFamily = FontFamily(Font(Res.font.sf_pro)),
                        fontWeight = FontWeight(600),
                        color = BeumColors.Black,
                    )
                )
                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = item.content, style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 22.sp,
                        fontFamily = FontFamily(Font(Res.font.sf_pro)),
                        fontWeight = FontWeight(400),
                        color = BeumColors.baseGrayLightGray800,
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "#연봉협상 #환승이직", style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 22.sp,
                        fontFamily = FontFamily(Font(Res.font.sf_pro)),
                        fontWeight = FontWeight(400),
                        color = BeumColors.primaryPrimarySkyblue,
                    )
                )
                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.height(20.dp).fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(Res.drawable.ic_heart_empty),
                        contentDescription = ""
                    )
                    Text(
                        text = "10", style = TextStyle(
                            fontSize = 13.sp,
                            lineHeight = 20.sp,
                            fontFamily = FontFamily(Font(Res.font.sf_pro)),
                            fontWeight = FontWeight(400),
                            color = BeumColors.GrayGray500,
                        )
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Image(
                        painter = painterResource(Res.drawable.ic_reply), contentDescription = ""
                    )
                    Text(
                        text = "10", style = TextStyle(
                            fontSize = 13.sp,
                            lineHeight = 20.sp,
                            fontFamily = FontFamily(Font(Res.font.sf_pro)),
                            fontWeight = FontWeight(400),
                            color = BeumColors.GrayGray500,
                        )
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        text = "조회 38회", style = TextStyle(
                            fontSize = BeumTypo.TypoScaleText75,
                            fontFamily = FontFamily(Font(Res.font.sf_pro)),
                            fontWeight = FontWeight(400),
                            color = BeumColors.baseGrayLightGray500,
                        )
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier.height(1.dp).fillMaxWidth()
                        .background(BeumColors.baseGrayLightGray100)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "답글", style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = BeumTypo.lineHeightBody3,
                        fontFamily = FontFamily(Font(Res.font.sf_pro)),
                        fontWeight = FontWeight(500),
                        color = BeumColors.baseGrayLightGray800,
                    )
                )
                Spacer(modifier = Modifier.height(24.dp))
            }
        }


    }
}