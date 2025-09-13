package com.kal.beum.home.presentation

import androidx.compose.foundation.Image
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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import beumproject.composeapp.generated.resources.Res
import beumproject.composeapp.generated.resources.angel
import beumproject.composeapp.generated.resources.devil
import beumproject.composeapp.generated.resources.icon_arrow_right
import beumproject.composeapp.generated.resources.sf_pro
import com.kal.beum.core.presentation.BeumColors
import com.kal.beum.core.presentation.BeumDimen
import com.kal.beum.core.presentation.ToastInfo
import com.kal.beum.home.presentation.components.AlarmButton
import com.kal.beum.home.presentation.components.FlowRow
import com.kal.beum.home.presentation.components.ToggleButton
import com.kal.beum.main.presentation.FullScreenType
import com.kal.beum.main.presentation.MainAction
import com.kal.beum.notice.presentaion.NoticeScreen
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreen(
    toggle: Boolean, onAction: (MainAction) -> Unit
) {
    val viewModel = koinViewModel<HomeViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.fillMaxWidth().height(64.dp), contentAlignment = Alignment.Center
        ) {
            ToggleButton(toggle) {
                onAction(MainAction.ToggleDevil(it))
            }

            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.weight(1f))
                AlarmButton(toggle) {
                    onAction(MainAction.PushFullScreen(FullScreenType.NoticeScreen))
                }
                Spacer(modifier = Modifier.width(20.dp))
            }
        }

        Spacer(modifier = Modifier.height(40.dp).fillMaxWidth())


        Image(
            modifier = Modifier.fillMaxWidth().padding(start = 120.dp, end = 120.dp)
                .padding(start = 9.5.dp, top = 28.dp, end = 9.5.dp, bottom = 29.dp),
            painter = painterResource(
                if (toggle) Res.drawable.devil else Res.drawable.angel
            ),
            contentScale = ContentScale.Fit,
            contentDescription = ""
        )

        Text(
            modifier = Modifier.width(272.dp).height(72.dp),
            text = if (toggle) "직장인들이여\n함께 분노합시다!" else "직장인들의 \n응원과 위로가 필요하다면?",
            style = TextStyle(
                fontSize = 22.sp,
                lineHeight = 36.08.sp,
                fontFamily = FontFamily(Font(Res.font.sf_pro)),
                fontWeight = FontWeight(700),
                color = if (toggle) BeumColors.baseGrayDarkGray700 else BeumColors.baseGrayLightGray800,
                textAlign = TextAlign.Center,
            )
        )

        Spacer(modifier = Modifier.height(60.dp).fillMaxWidth())

        FlowRow(toggle, viewModel)

        Spacer(modifier = Modifier.weight(1f))
        Row(
            modifier = Modifier.width(350.dp).height(52.dp).background(
                color = if (toggle) BeumColors.baseAlphaWhiteLightWhite else BeumColors.baseGrayLightGray900,
                shape = RoundedCornerShape(size = BeumDimen.radius100)
            ).padding(start = BeumDimen.Px15RemSpacing09, end = BeumDimen.Px15RemSpacing09)
                .clickable {

                },
            horizontalArrangement = Arrangement.spacedBy(0.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "고민을 남겨보세요", style = TextStyle(
                    fontSize = BeumDimen.TypoScaleText300,
                    lineHeight = BeumDimen.baseLineheightTextLineheight300,
                    fontFamily = FontFamily(Font(Res.font.sf_pro)),
                    fontWeight = FontWeight(700),
                    color = if (toggle) BeumColors.Black else BeumColors.White,
                    textAlign = TextAlign.Center,
                )
            )
//
            Spacer(modifier = Modifier.width(6.dp))

            Icon(
                painter = painterResource(Res.drawable.icon_arrow_right),
                tint = if (toggle) BeumColors.Black else BeumColors.White,
                contentDescription = ""
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
    }
}