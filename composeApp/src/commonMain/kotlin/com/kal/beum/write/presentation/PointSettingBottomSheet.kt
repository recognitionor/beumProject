package com.kal.beum.write.presentation// DynamicInfoBottomSheet.kt (새 파일 또는 com.kal.beum.write.presentation.InfoBottomSheet 정의 옆에 추가)

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import beumproject.composeapp.generated.resources.Res
import beumproject.composeapp.generated.resources.sf_pro
import com.kal.beum.core.presentation.BeumColors
import com.kal.beum.core.presentation.BeumDimen
import com.kal.beum.core.presentation.BeumTypo
import org.jetbrains.compose.resources.Font

@Composable
fun PointSettingBottomSheet(point: Int, onValueChange: (point: Int) -> Unit) {

    Column(modifier = Modifier.padding(20.dp)) {
        Text(
            text = "포인트 걸기", style = TextStyle(
                fontSize = BeumTypo.TypoScaleText500,
                lineHeight = 32.sp,
                fontFamily = FontFamily(Font(Res.font.sf_pro)),
                fontWeight = FontWeight(700),
                color = BeumColors.baseGrayLightGray900,
            )
        )
        Spacer(modifier = Modifier.height(40.dp))
        Box(modifier = Modifier.height(40.dp)) {
            BasicTextField(
                value = if (point == 0) "" else point.toString(),
                onValueChange = {
                    val newValue = it.filter { ch -> ch.isDigit() }
                    if (newValue.isNotEmpty()) {
                        onValueChange.invoke(newValue.toInt())
                    } else {
                        onValueChange.invoke(0) // 또는 원하는 기본값
                    }
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth().fillMaxHeight(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                )
            )

            if (point == 0) {
                Text(
                    text = "최대 1,000 하트 까지 입력가능", style = TextStyle(
                        fontSize = BeumTypo.TypoScaleText150,
                        lineHeight = BeumDimen.TypoLienheigtLineheight200,
                        fontFamily = FontFamily(Font(Res.font.sf_pro)),
                        fontWeight = FontWeight(500),
                        color = BeumColors.baseGrayLightGray500,
                    )
                )
            }
        }
        Box(
            modifier = Modifier.height(1.dp).fillMaxWidth()
                .background(BeumColors.baseGrayLightGray200),
        )
        Spacer(modifier = Modifier.fillMaxWidth().height(6.dp))
        Row(modifier = Modifier.height(22.dp).fillMaxWidth()) {
            Text(
                text = "내 보유 하트", style = TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 21.98.sp,
                    fontFamily = FontFamily(Font(Res.font.sf_pro)),
                    fontWeight = FontWeight(500),
                    color = BeumColors.baseGrayLightGray600,
                )
            )

            Spacer(modifier = Modifier.fillMaxWidth().height(16.dp))
        }

        Spacer(modifier = Modifier.fillMaxWidth().height(14.dp))
        Row(
            modifier = Modifier.height(35.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp) // ← 간격 추가
        ) {
            val list = listOf(10, 50, 100, 500, 1000)
            list.forEach {
                Box(
                    modifier = Modifier.background(
                        color = BeumColors.baseAlphaBlackLightBlack50A,
                        shape = RoundedCornerShape(size = BeumDimen.radius75)
                    ).padding(start = 12.dp, top = 10.dp, end = 12.dp, bottom = 10.dp)
                ) {
                    Text(
                        text = "+${it}", style = TextStyle(
                            fontSize = 14.sp,
                            lineHeight = 14.17.sp,
                            fontFamily = FontFamily(Font(Res.font.sf_pro)),
                            fontWeight = FontWeight(500),
                            color = BeumColors.baseGrayLightGray500,
                            textAlign = TextAlign.Center,
                        )
                    )
                }
            }
        }
        Spacer(modifier = Modifier.fillMaxWidth().height(28.dp))

        Button(modifier = Modifier.fillMaxWidth().height(52.dp).background(
            color = BeumColors.baseGrayLightGray900,
            shape = RoundedCornerShape(size = BeumDimen.radius100),
        ).padding(
            start = BeumDimen.Px15RemSpacing09,
            top = 0.dp,
            end = BeumDimen.Px15RemSpacing09,
            bottom = 0.dp
        ), colors = ButtonDefaults.buttonColors(
            containerColor = BeumColors.Black, contentColor = Color.White
        ), onClick = {}) {
            Text(
                text = "하트 올려놓기", style = TextStyle(
                    fontSize = BeumTypo.TypoScaleText300,
                    fontFamily = FontFamily(Font(Res.font.sf_pro)),
                    fontWeight = FontWeight(700),
                    color = BeumColors.White,
                    textAlign = TextAlign.Center,
                )
            )
        }

    }
}