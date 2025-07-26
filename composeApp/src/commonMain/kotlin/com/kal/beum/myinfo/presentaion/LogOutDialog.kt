package com.kal.beum.myinfo.presentaion

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import beumproject.composeapp.generated.resources.Res
import beumproject.composeapp.generated.resources.sf_pro
import com.kal.beum.core.presentation.BeumColors
import com.kal.beum.core.presentation.BeumDimen
import com.kal.beum.core.presentation.BeumTypo
import org.jetbrains.compose.resources.Font

@Composable
fun LogOutDialog(onDismiss: () -> Unit, logoutClick: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.5f)) // 배경 반투명 처리
            .clickable { onDismiss.invoke() }, contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(
                start = 20.dp,
                top = BeumDimen.Px125RemSpacing08,
                end = 20.dp,
                bottom = BeumDimen.Px075RemSpacing06
            ).fillMaxWidth().height(200.dp).background(
                color = BeumColors.White, shape = RoundedCornerShape(
                    topStart = 12.dp, topEnd = 12.dp, bottomStart = 12.dp, bottomEnd = 12.dp
                )
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = "로그아웃 하시겠습니까?", style = TextStyle(
                    fontSize = BeumTypo.TypoScaleText400,
                    lineHeight = BeumTypo.TypoLienheigtLineheight500,
                    fontFamily = FontFamily(Font(Res.font.sf_pro)),
                    fontWeight = FontWeight(700),
                    color = BeumColors.baseGrayLightGray800,
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(modifier = Modifier.padding(20.dp)) {
                Box(
                    modifier = Modifier.clickable {
                        onDismiss.invoke()
                    }.weight(1f).background(
                        color = BeumColors.baseGrayLightGray100,
                        shape = RoundedCornerShape(size = 12.dp)
                    ).padding(20.dp), contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "취소", style = TextStyle(
                            fontSize = BeumTypo.TypoScaleText150,
                            lineHeight = BeumTypo.baseLineheightTextLineheight300,
                            fontFamily = FontFamily(Font(Res.font.sf_pro)),
                            fontWeight = FontWeight(700),
                            color = BeumColors.baseGrayLightGray900,
                            textAlign = TextAlign.Center,
                        )
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Box(
                    modifier = Modifier.clickable {
                        logoutClick()
                    }.weight(1f).background(
                        color = BeumColors.PrimarySkyBlue, shape = RoundedCornerShape(size = 12.dp)
                    ).padding(20.dp), contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "로그아웃", style = TextStyle(
                            fontSize = BeumTypo.TypoScaleText150,
                            fontFamily = FontFamily(Font(Res.font.sf_pro)),
                            fontWeight = FontWeight(700),
                            color = BeumColors.White,
                            textAlign = TextAlign.Center,
                        )
                    )
                }
            }
        }
    }
}