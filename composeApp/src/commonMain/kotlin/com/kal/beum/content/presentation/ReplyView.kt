package com.kal.beum.content.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import beumproject.composeapp.generated.resources.Res
import beumproject.composeapp.generated.resources.angel_abled
import beumproject.composeapp.generated.resources.heart
import beumproject.composeapp.generated.resources.ic_angel_emoji
import beumproject.composeapp.generated.resources.ic_dot
import beumproject.composeapp.generated.resources.ic_reply
import beumproject.composeapp.generated.resources.ic_small_heart
import beumproject.composeapp.generated.resources.sf_pro
import com.kal.beum.content.domain.ReplyInfo
import com.kal.beum.core.presentation.BeumColors
import com.kal.beum.core.presentation.BeumDimen
import com.kal.beum.core.presentation.BeumTypo
import com.kal.beum.utils.formatTimeAgo
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource

@Composable
fun ReplyView(replyInfo: ReplyInfo, selectedDetailReview: (ReplyInfo) -> Unit) {
    Row {

        Box(
            modifier = Modifier.border(
                width = 0.625.dp,
                color = BeumColors.baseAlphaWhiteLightWhite500A,
                shape = RoundedCornerShape(size = 20.dp)
            ).width(24.dp).height(24.dp).background(
                color = Color(0xFF45CAF7), shape = RoundedCornerShape(size = 20.dp)
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
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = replyInfo.writer, style = TextStyle(
                        fontSize = BeumTypo.TypoScaleText100,
                        lineHeight = BeumDimen.lineHeightBody3,
                        fontFamily = FontFamily(Font(Res.font.sf_pro)),
                        fontWeight = FontWeight(600),
                        color = BeumColors.baseGrayLightGray800,
                    )
                )
                Spacer(modifier = Modifier.width(6.dp))

                Text(
                    text = formatTimeAgo(replyInfo.lastModifiedTime), style = TextStyle(
                        fontSize = BeumTypo.TypoScaleText100,
                        lineHeight = 20.sp,
                        fontFamily = FontFamily(Font(Res.font.sf_pro)),
                        fontWeight = FontWeight(400),
                        color = BeumColors.baseGrayLightGray500,
                    )
                )
                Spacer(modifier = Modifier.width(6.dp))
                Image(painter = painterResource(Res.drawable.ic_dot), contentDescription = "")
                Spacer(modifier = Modifier.width(6.dp))
                Box {
                    Image(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(Res.drawable.angel_abled),
                        contentDescription = "",
                    )
                    Image(
                        modifier = Modifier.size(12.dp).align(Alignment.BottomStart),
                        painter = painterResource(Res.drawable.ic_small_heart),
                        contentDescription = ""
                    )
                }



                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "채택", style = TextStyle(
                        fontSize = BeumTypo.TypoScaleText100,
                        lineHeight = 20.sp,
                        fontFamily = FontFamily(Font(Res.font.sf_pro)),
                        fontWeight = FontWeight(700),
                        color = BeumColors.angelSkyblue,
                    )
                )
            }
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = replyInfo.content, style = TextStyle(
                    fontSize = BeumTypo.TypoScaleText150,
                    lineHeight = 22.sp,
                    fontFamily = FontFamily(Font(Res.font.sf_pro)),
                    fontWeight = FontWeight(400),
                    color = BeumColors.baseGrayLightGray800,
                )
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    modifier = Modifier.size(20.dp),
                    painter = painterResource(Res.drawable.heart),
                    contentDescription = ""
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = replyInfo.likeCount.toString(), style = TextStyle(
                        fontSize = 13.sp,
                        lineHeight = 20.sp,
                        fontFamily = FontFamily(Font(Res.font.sf_pro)),
                        fontWeight = FontWeight(400),
                        color = BeumColors.GrayGray500,
                    )
                )
                Spacer(modifier = Modifier.width(24.dp))

                Image(painter = painterResource(Res.drawable.ic_reply), contentDescription = "")
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = replyInfo.replyList.size.toString(), style = TextStyle(
                        fontSize = 13.sp,
                        lineHeight = 20.sp,
                        fontFamily = FontFamily(Font(Res.font.sf_pro)),
                        fontWeight = FontWeight(400),
                        color = BeumColors.GrayGray500,
                    )
                )
                Spacer(modifier = Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                modifier = Modifier.clickable {
                    selectedDetailReview.invoke(replyInfo)
                }, text = "답글 ${replyInfo.replyList.size}개 더 보기", style = TextStyle(
                    fontSize = BeumTypo.TypoScaleText150,
                    lineHeight = 20.sp,
                    fontFamily = FontFamily(Font(Res.font.sf_pro)),
                    fontWeight = FontWeight(700),
                    color = BeumColors.angelSkyblue,
                )
            )

            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier.fillMaxWidth().height(1.dp)
                    .background(BeumColors.baseGrayLightGray100)
            )
        }
    }
}