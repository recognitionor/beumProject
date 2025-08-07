package com.kal.beum.main.presentation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationEndReason
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import beumproject.composeapp.generated.resources.Res
import beumproject.composeapp.generated.resources.apple
import beumproject.composeapp.generated.resources.google
import beumproject.composeapp.generated.resources.img_wings_set
import beumproject.composeapp.generated.resources.kakao
import beumproject.composeapp.generated.resources.sf_pro
import com.kal.beum.core.presentation.BeumColors
import com.kal.beum.core.presentation.BeumColors.White
import com.kal.beum.core.presentation.BeumDimen
import com.kal.beum.core.presentation.BeumDimen.Px1RemSpacing07
import com.kal.beum.core.presentation.BeumTypo
import com.kal.beum.main.domain.SocialType
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun SplashScreen() {
    Column(
        modifier = Modifier.fillMaxSize().background(color = White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val viewModel = koinViewModel<MainViewModel>()
        val state by viewModel.state.collectAsStateWithLifecycle()

        val topSpacerHeight = remember { Animatable(300f) } // 시작은 250.dp
        val titleAlpha = remember { Animatable(0f) }
        val descriptionAlpha = remember { Animatable(0f) }
        val buttonAlpha = remember { Animatable(0f) }

        LaunchedEffect(Unit) {
            val topSpacerHeightResult = topSpacerHeight.animateTo(
                targetValue = 140f, // 줄일 최종 높이 (상단 1/3 정도 느낌)
                animationSpec = tween(durationMillis = 1000)
            )
            if (topSpacerHeightResult.endReason == AnimationEndReason.Finished) {
                val titleAlphaResult = titleAlpha.animateTo(
                    targetValue = 1f, animationSpec = tween(durationMillis = 500)
                )
                if (titleAlphaResult.endReason == AnimationEndReason.Finished) {
                    val descriptionAlphaResult = descriptionAlpha.animateTo(
                        targetValue = 1f, animationSpec = tween(durationMillis = 500)
                    )
                    if (descriptionAlphaResult.endReason == AnimationEndReason.Finished) {
                        buttonAlpha.animateTo(
                            targetValue = 1f, animationSpec = tween(durationMillis = 300)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(topSpacerHeight.value.dp)) // 여기를 애니메이션


        Image(
            modifier = Modifier.width(132.dp).height(56.dp),
            painter = painterResource(Res.drawable.img_wings_set),
            contentDescription = ""
        )

        Spacer(modifier = Modifier.fillMaxWidth().height(20.dp))

        Text(
            text = "비움에 오신것을\n환영해요!", style = TextStyle(
                fontSize = BeumTypo.TypoScaleText600,
                lineHeight = BeumTypo.TypoLineHeight700,
                fontFamily = FontFamily(Font(Res.font.sf_pro)),
                fontWeight = FontWeight(700),
                color = BeumColors.Gray_800,
                textAlign = TextAlign.Center,
            ), modifier = Modifier.alpha(titleAlpha.value)
        )
        Spacer(modifier = Modifier.fillMaxWidth().height(Px1RemSpacing07))

        Text(
            text = "비움에서 나의 고민을 함께 나눠요", style = TextStyle(
                fontSize = BeumTypo.TypoScaleText300,
                lineHeight = 26.sp,
                fontFamily = FontFamily(Font(Res.font.sf_pro)),
                fontWeight = FontWeight(500),
                color = BeumColors.Gray_400,
                textAlign = TextAlign.Center,
            ), modifier = Modifier.alpha(descriptionAlpha.value)
        )

        if (state.userInfo == null) {
            Column(
                modifier = Modifier.fillMaxWidth().height(844.dp).background(color = White)
                    .alpha(buttonAlpha.value).padding(start = 20.dp, end = 20.dp, bottom = 45.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SocialLoginBtn(
                    BeumColors.KaKaoColor,
                    BeumColors.KaKaoColor,
                    SocialType.KAKAO,
                    painterResource(Res.drawable.kakao),
                    BeumColors.Black
                ) {
                    viewModel.socialLogin(SocialType.NAVER_CODE)
                }
                Spacer(modifier = Modifier.height(10.dp))
                SocialLoginBtn(
                    White,
                    BeumColors.baseAlphaBlackDarkBlack200A,
                    SocialType.GOOGLE,
                    painterResource(Res.drawable.google),
                    BeumColors.Black
                ) {
                    viewModel.socialLogin(SocialType.GOOGLE_CODE)
                }
                Spacer(modifier = Modifier.height(10.dp))
                SocialLoginBtn(
                    BeumColors.Black,
                    BeumColors.Black,
                    SocialType.APPLE,
                    painterResource(Res.drawable.apple),
                    White
                ) {
                    viewModel.socialLogin(SocialType.APPLE_CODE)
                }

            }
        } else {
            Spacer(modifier = Modifier.height(844.dp).fillMaxWidth())
        }
    }
}

@Composable
fun SocialLoginBtn(
    backgroundColor: Color,
    borderColor: Color,
    socialName: String,
    paint: Painter,
    textColor: Color,
    clickable: () -> Unit
) {
    Row(
        modifier = Modifier.border(
            width = 1.dp,
            color = borderColor,
            shape = RoundedCornerShape(size = BeumDimen.Px4RemSpacing15)
        ).clip(RoundedCornerShape(size = BeumDimen.Px4RemSpacing15)).clickable {
            clickable.invoke()
        }.fillMaxWidth().height(52.dp).background(
            color = backgroundColor, shape = RoundedCornerShape(size = BeumDimen.Px4RemSpacing15)
        ).padding(
            start = BeumDimen.Px15RemSpacing09,
            top = 0.dp,
            end = BeumDimen.Px15RemSpacing09,
            bottom = 0.dp
        ), verticalAlignment = Alignment.CenterVertically

    ) {

        Image(painter = paint, contentDescription = socialName)
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "${socialName}로 계속하기", style = TextStyle(
                fontSize = BeumTypo.TypoScaleText300,
                lineHeight = BeumTypo.baseLineheightTextLineheight300,
                fontFamily = FontFamily(Font(Res.font.sf_pro)),
                fontWeight = FontWeight(700),
                color = textColor,
                textAlign = TextAlign.Center,
            )
        )
        Spacer(modifier = Modifier.weight(1f))

    }
}