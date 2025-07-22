package com.kal.beum.myinfo.presentaion

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import beumproject.composeapp.generated.resources.Res
import beumproject.composeapp.generated.resources.angel_abled
import beumproject.composeapp.generated.resources.devil_abled
import beumproject.composeapp.generated.resources.ic_check
import beumproject.composeapp.generated.resources.sf_pro
import com.kal.beum.core.presentation.BeumColors
import com.kal.beum.core.presentation.BeumTypo
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource

@Composable
fun ModeToggleBottomSheet(
    isDevilMode: Boolean, onToggleMode: (Boolean) -> Unit
) {
    Column(modifier = Modifier.padding(20.dp)) {
        ModeToggleRowItem(Res.drawable.angel_abled, "천사", isDevilMode) {
            onToggleMode(false)
        }

        Spacer(modifier = Modifier.height(12.dp))

        ModeToggleRowItem(Res.drawable.devil_abled, "악마", !isDevilMode) {
            onToggleMode(true)
        }
    }
}

@Composable
fun ModeToggleRowItem(
    painter: DrawableResource, title: String, isSelected: Boolean, function: () -> Unit
) {
    Row(modifier = Modifier.height(60.dp).clickable {
        function.invoke()
    }, verticalAlignment = Alignment.CenterVertically) {
        Image(painter = painterResource(painter), contentDescription = "")
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = title, style = TextStyle(
                fontSize = BeumTypo.TypoScaleText400,
                lineHeight = 32.sp,
                fontFamily = FontFamily(Font(Res.font.sf_pro)),
                fontWeight = FontWeight(700),
                color = BeumColors.baseGrayLightGray800,
            )
        )

        Spacer(modifier = Modifier.weight(1f))
        if (!isSelected) {
            Image(
                painter = painterResource(Res.drawable.ic_check),
                contentDescription = "",
            )
        }
    }
}