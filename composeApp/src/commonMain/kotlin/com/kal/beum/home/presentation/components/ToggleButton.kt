package com.kal.beum.home.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import beumproject.composeapp.generated.resources.Res
import beumproject.composeapp.generated.resources.angel_abled
import beumproject.composeapp.generated.resources.angel_disabled
import beumproject.composeapp.generated.resources.devil_abled
import beumproject.composeapp.generated.resources.devil_disabled
import org.jetbrains.compose.resources.painterResource

@Composable
fun ToggleButton(modifier: Modifier = Modifier, toggle: (Boolean) -> Unit) {
    var isToggled by remember { mutableStateOf(false) }

    Box(
        modifier = modifier.clip(RoundedCornerShape(50)).width(136.dp).height(72.dp)
            .wrapContentSize(Alignment.Center)
            .background(if (isToggled) Color.Black else Color.White).clickable {
                isToggled = !isToggled
                toggle.invoke(isToggled)
            }.padding(4.dp), contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier.clip(RoundedCornerShape(90))
                    .background(if (isToggled) Color.Transparent else Color.Black).width(64.dp)
            ) {
                Image(
                    modifier = Modifier.size(48.dp),
                    painter = if (isToggled) painterResource(Res.drawable.angel_disabled) else painterResource(
                        Res.drawable.angel_abled
                    ),
                    contentDescription = "Mood Icon",
                )
            }

            Box(
                modifier.clip(RoundedCornerShape(90))
                    .background(if (isToggled) Color.White else Color.Transparent).width(64.dp)
            ) {
                Image(
                    modifier = Modifier.size(48.dp),
                    painter = if (isToggled) painterResource(Res.drawable.devil_abled) else painterResource(
                        Res.drawable.devil_disabled
                    ),
                    contentDescription = "Alternate Mood Icon",
                )
            }

        }
    }
}