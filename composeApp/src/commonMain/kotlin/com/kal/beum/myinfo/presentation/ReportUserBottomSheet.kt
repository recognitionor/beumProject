package com.kal.beum.myinfo.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import beumproject.composeapp.generated.resources.sf_pro
import com.kal.beum.common.BeumConstants
import com.kal.beum.core.presentation.BeumColors
import com.kal.beum.core.presentation.BeumDimen
import com.kal.beum.core.presentation.BeumTypo
import org.jetbrains.compose.resources.Font


@Composable
fun ReportUserBottomSheet(report: (Int) -> Unit) {
    var reportReasonIndex by remember { mutableStateOf(0) }

    Column(modifier = Modifier.padding(horizontal = 20.dp)) {
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "신고하는 이유를 선택해주세요.", style = TextStyle(
                fontSize = BeumTypo.TypoScaleText400,
                lineHeight = BeumTypo.TypoLienheigtLineheight500,
                fontFamily = FontFamily(Font(Res.font.sf_pro)),
                fontWeight = FontWeight(700),
                color = BeumColors.baseGrayLightGray800,
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        Column(
            modifier = Modifier.fillMaxWidth().shadow(
                elevation = 20.dp, spotColor = Color(0x0D000000), ambientColor = Color(0x0D000000)
            ).background(
                color = BeumColors.baseAlphaWhiteDarkWhite,
                shape = RoundedCornerShape(size = BeumDimen.radius150)
            ).padding(
                start = BeumDimen.Px125RemSpacing08,
                top = BeumDimen.Px075RemSpacing06,
                end = BeumDimen.Px125RemSpacing08,
                bottom = BeumDimen.Px075RemSpacing06
            )
        ) {
            BeumConstants.USER_REPORT_REASONS.forEachIndexed { i, s ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth().clickable {
                        reportReasonIndex = i
                    }) {
                    RadioButton(
                        selected = reportReasonIndex == i, onClick = {
                            reportReasonIndex = i
                        })
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = s, style = TextStyle(
                            fontSize = BeumTypo.TypoScaleText300,
                            lineHeight = 32.sp,
                            fontFamily = FontFamily(Font(Res.font.sf_pro)),
                            fontWeight = FontWeight(500),
                            color = BeumColors.baseGrayLightGray900,
                        )
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier.fillMaxWidth().background(
                color = BeumColors.PrimarySkyBlue,
                shape = RoundedCornerShape(size = BeumDimen.radius100)
            ).fillMaxWidth().height(45.dp).clickable {
                report.invoke(reportReasonIndex)
            }, contentAlignment = Alignment.Center
        ) {
            Text(
                text = "확인", style = TextStyle(
                    fontSize = BeumTypo.TypoScaleText300,
                    fontFamily = FontFamily(Font(Res.font.sf_pro)),
                    fontWeight = FontWeight(700),
                    color = BeumColors.White,
                    textAlign = TextAlign.Center,
                )
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
    }
}