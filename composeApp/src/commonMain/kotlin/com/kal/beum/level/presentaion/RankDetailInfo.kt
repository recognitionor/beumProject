package com.kal.beum.level.presentaion

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import beumproject.composeapp.generated.resources.Res
import beumproject.composeapp.generated.resources.angel
import beumproject.composeapp.generated.resources.devil
import beumproject.composeapp.generated.resources.heart
import beumproject.composeapp.generated.resources.ic_devil_fire
import beumproject.composeapp.generated.resources.sf_pro
import coil3.compose.AsyncImage
import com.kal.beum.core.presentation.BeumColors
import com.kal.beum.core.presentation.BeumTypo
import com.kal.beum.level.domain.RankerUserInfo
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource

@Composable
fun RankDetailInfo(isDevil: Boolean, ranker: RankerUserInfo, onClickItem: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().height(80.dp)
            .padding(vertical = 16.dp, horizontal = 20.dp).clickable {
                onClickItem.invoke()
            }, verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = ranker.rank.toString(), style = TextStyle(
                fontSize = 16.sp,
                lineHeight = BeumTypo.lineHeightBody3,
                fontFamily = FontFamily(Font(Res.font.sf_pro)),
                fontWeight = FontWeight(700),
                color = if (isDevil) if (ranker.rank > 3) BeumColors.White else BeumColors.DevilPrimary else if (ranker.rank > 3) BeumColors.Black else BeumColors.angelSkyblue,
                textAlign = TextAlign.Center,
            )
        )
        Spacer(modifier = Modifier.width(12.dp))

        if (ranker.profileImageUrl.isNotEmpty()) {
            Box(
                modifier = Modifier.width(48.dp).height(48.dp).background(
                    color = Color.Transparent,
                    shape = RoundedCornerShape(size = 20.dp)
                )
            ) {
                AsyncImage(
                    model = ranker.profileImageUrl,
                    contentDescription = "",
                    modifier = Modifier.size(48.dp)
                )
            }

        } else {
            Box(
                modifier = Modifier.border(
                    width = 0.625.dp,
                    color = BeumColors.baseAlphaWhiteLightWhite500A,
                    shape = RoundedCornerShape(size = 20.dp)
                ).width(48.dp).height(48.dp).background(
                    color = if (isDevil) BeumColors.DevilPrimary else Color(0xFF45CAF7),
                    shape = RoundedCornerShape(size = 20.dp)
                )
            ) {
                Image(
                    painter = painterResource(if (isDevil) Res.drawable.devil else Res.drawable.angel),
                    contentDescription = "",
                    modifier = Modifier.size(48.dp)
                )
            }
        }
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = ranker.nickname, modifier = Modifier.weight(1f), style = TextStyle(
                fontSize = 16.sp,
                lineHeight = BeumTypo.lineHeightBody3,
                fontFamily = FontFamily(Font(Res.font.sf_pro)),
                fontWeight = FontWeight(700),
                color = if (isDevil) BeumColors.White else BeumColors.baseAlphaBlackDarkBlack700A,
            )
        )

        Text(
            text = ranker.score.toString(), style = TextStyle(
                fontSize = BeumTypo.TypoScaleText200,
                lineHeight = BeumTypo.lineHeightCaption1,
                fontFamily = FontFamily(Font(Res.font.sf_pro)),
                fontWeight = FontWeight(400),
                color = if (isDevil) BeumColors.baseAlphaWhiteLightWhite500A else BeumColors.baseAlphaBlackDarkBlack500A,
                textAlign = TextAlign.Right,
            )
        )

        Spacer(modifier = Modifier.width(4.dp))

        Box(
            modifier = Modifier.width(16.dp).height(16.dp).background(
                color = BeumColors.baseCoolGrayLightGray700,
                shape = RoundedCornerShape(size = 66.66668.dp)
            ).padding(
                start = 2.66667.dp, top = 2.66667.dp, end = 2.66667.dp, bottom = 2.66667.dp
            ), contentAlignment = Alignment.Center
        ) {
            Image(
                painter = if (isDevil) painterResource(Res.drawable.ic_devil_fire) else painterResource(
                    Res.drawable.heart
                ), contentDescription = ""
            )
        }
    }
}