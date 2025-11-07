package com.kal.beum.myinfo.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import beumproject.composeapp.generated.resources.Res
import beumproject.composeapp.generated.resources.ic_report
import beumproject.composeapp.generated.resources.ic_share
import beumproject.composeapp.generated.resources.sf_pro
import com.kal.beum.core.presentation.BeumColors
import com.kal.beum.core.presentation.BeumDimen
import com.kal.beum.core.presentation.BeumTypo
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportBottomSheetPage(sheetState: () -> Unit, reportClick: () -> Unit) {
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxWidth().wrapContentHeight().background(
            color = BeumColors.baseGrayLightGray75, shape = RoundedCornerShape(
                topStart = 12.dp, topEnd = 12.dp, bottomStart = 0.dp, bottomEnd = 0.dp
            )
        ).padding(20.dp)
    ) {

        Column(
            modifier = Modifier.shadow(
                elevation = 20.dp, spotColor = Color(0x0D000000), ambientColor = Color(0x0D000000)
            ).fillMaxWidth().background(
                color = BeumColors.baseAlphaWhiteDarkWhite,
                shape = RoundedCornerShape(size = BeumDimen.radius150)
            ).padding(vertical = 20.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 10.dp)
                    .clickable {
                        reportClick.invoke()
                    }, verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier.size(20.dp),
                    painter = painterResource(Res.drawable.ic_report),
                    contentDescription = ""
                )
                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "신고하기", style = TextStyle(
                        fontSize = BeumTypo.TypoScaleText300,
                        fontFamily = FontFamily(Font(Res.font.sf_pro)),
                        fontWeight = FontWeight(500),
                        color = Color(0xFFFE5B58),
                    )
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier.size(20.dp),
                    painter = painterResource(Res.drawable.ic_share),
                    colorFilter = ColorFilter.tint(BeumColors.Black),
                    contentDescription = ""
                )
                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "공유하기", style = TextStyle(
                        fontSize = BeumTypo.TypoScaleText300,
                        fontFamily = FontFamily(Font(Res.font.sf_pro)),
                        fontWeight = FontWeight(500),
                        color = BeumColors.Black,
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))
        Box(
            modifier = Modifier.shadow(
                elevation = 20.dp, spotColor = Color(0x0D000000), ambientColor = Color(0x0D000000)
            ).background(
                color = BeumColors.baseAlphaWhiteDarkWhite,
                shape = RoundedCornerShape(size = BeumDimen.radius150)
            ).fillMaxWidth().padding(
                start = BeumDimen.Px125RemSpacing08,
                top = BeumDimen.Px075RemSpacing06,
                end = BeumDimen.Px125RemSpacing08,
                bottom = BeumDimen.Px075RemSpacing06
            ).clickable {
                sheetState.invoke()
            }, contentAlignment = Alignment.Center
        ) {
            Text(
                text = "닫기", style = TextStyle(
                    fontSize = BeumDimen.TypoScaleText300,
                    fontFamily = FontFamily(Font(Res.font.sf_pro)),
                    fontWeight = FontWeight(500),
                    color = BeumColors.baseGrayLightGray800,
                    textAlign = TextAlign.Center,
                )
            )
        }
    }
}
