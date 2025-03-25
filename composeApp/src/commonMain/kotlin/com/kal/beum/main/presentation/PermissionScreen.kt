package com.kal.beum.main.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import beumproject.composeapp.generated.resources.Res
import beumproject.composeapp.generated.resources.sf_pro
import com.kal.beum.core.presentation.BeumButton
import com.kal.beum.core.presentation.BeumColors
import com.kal.beum.core.presentation.BeumDimen
import com.kal.beum.core.presentation.BeumTypo
import org.jetbrains.compose.resources.Font

@Composable
fun PermissionScreen() {
    Column(
        modifier = Modifier.padding(
            start = BeumDimen.Px125RemSpacing08,
            end = BeumDimen.Px125RemSpacing08,
            top = 71.dp,
            bottom = BeumDimen.Px125RemSpacing08
        )
    ) {
        Text(
            text = "앱 사용을 위해\n접근 권한을 허용해 주세요", style = TextStyle(
                fontSize = BeumTypo.TypoScaleText700,
                lineHeight = 42.sp,
                fontFamily = FontFamily(Font(Res.font.sf_pro)),
                fontWeight = FontWeight(700),
                color = BeumColors.baseGrayLightGray800,
            )
        )

        Spacer(modifier = Modifier.height(80.dp))

        Text(
            text = "알림 [선택]", style = TextStyle(
                fontSize = BeumTypo.TypoScaleText200,
                lineHeight = BeumTypo.lineHeightBody4,
                fontFamily = FontFamily(Font(Res.font.sf_pro)),
                fontWeight = FontWeight(600),
                color = BeumColors.baseGrayLightGray600,
            )
        )

        Spacer(modifier = Modifier.height(BeumDimen.Px075RemSpacing06))

        Text(
            text = "고민 작성, 답글, 공지, 이벤트 알림 수신", style = TextStyle(
                fontSize = BeumTypo.TypoScaleText300,
                lineHeight = BeumTypo.lineHeightBody4,
                fontFamily = FontFamily(Font(Res.font.sf_pro)),
                fontWeight = FontWeight(500),
                color = BeumColors.baseGrayLightGray500,
            )
        )
        Spacer(modifier = Modifier.height(BeumDimen.Px1RemSpacing07))
        Box(
            modifier = Modifier.border(width = 1.dp, color = BeumColors.baseGrayLightGray100)
                .padding(1.dp).width(350.dp).height(0.dp)
        )
        Spacer(modifier = Modifier.height(BeumDimen.Px1RemSpacing07))

        Text(
            text = "알림 접근 권한을 허용하지 않아도 서비스의 기능을 사용하실 수 있지만, 미동의 시 일부 기능 사용이 제한될 수 있습니다.",
            style = TextStyle(
                fontSize = BeumTypo.TypoScaleText150,
                lineHeight = BeumTypo.TypoScaleText500,
                fontFamily = FontFamily(Font(Res.font.sf_pro)),
                fontWeight = FontWeight(500),
                color = BeumColors.baseGrayLightGray400,
            )
        )

        Spacer(modifier = Modifier.weight(1f))

        BeumButton(
            modifier = Modifier.fillMaxWidth().height(52.dp), "확인"
        )
    }
}