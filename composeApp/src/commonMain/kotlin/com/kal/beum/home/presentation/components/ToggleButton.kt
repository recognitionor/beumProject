package com.kal.beum.home.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
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
fun ToggleButton(toggle: Boolean, toggleClicked: (Boolean) -> Unit) {
    var isToggled by remember { mutableStateOf(toggle) }

    Box(
        modifier = Modifier.height(48.dp).width(104.dp).border(
            width = 0.dp, color = Color.Transparent, shape = RoundedCornerShape(size = 100.dp)
        ).clip(RoundedCornerShape(size = 100.dp)).wrapContentSize(Alignment.Center)
            .background(if (isToggled) Color.Black else Color.White).clickable {
                isToggled = !isToggled
                toggleClicked.invoke(isToggled)
            }.padding(4.dp), contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                Modifier.clip(RoundedCornerShape(90))
                    .background(if (isToggled) Color.Transparent else Color.Black).width(48.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    modifier = Modifier.size(32.dp),
                    painter = if (isToggled) painterResource(Res.drawable.angel_disabled) else painterResource(
                        Res.drawable.angel_abled
                    ),
                    contentDescription = "Mood Icon",
                )
            }

            Box(
                Modifier.clip(RoundedCornerShape(90))
                    .background(if (isToggled) Color.White else Color.Transparent).width(48.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    modifier = Modifier.size(32.dp),
                    painter = if (isToggled) painterResource(Res.drawable.devil_abled) else painterResource(
                        Res.drawable.devil_disabled
                    ),
                    contentDescription = "Alternate Mood Icon",
                )
            }

        }
    }
}