package com.kal.beum.write.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import beumproject.composeapp.generated.resources.Res
import beumproject.composeapp.generated.resources.angel_abled
import beumproject.composeapp.generated.resources.devil_abled
import beumproject.composeapp.generated.resources.heart
import beumproject.composeapp.generated.resources.ic_clear
import beumproject.composeapp.generated.resources.icon_arrow_right
import beumproject.composeapp.generated.resources.icon_arrow_right_black
import beumproject.composeapp.generated.resources.img_info
import beumproject.composeapp.generated.resources.sf_pro
import com.kal.beum.core.presentation.BeumColors
import com.kal.beum.core.presentation.BeumDimen
import com.kal.beum.core.presentation.BeumTypo
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource

@Composable
fun WriteEditText(
    height: Dp = 88.dp,
    content: String,
    title: String,
    placeholder: String,
    click: (() -> Unit)? = null,
    infoClick: (() -> Unit)? = null,
    onTextChange: (String) -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    val borderColor = if (isFocused) BeumColors.SkyBlue else if (content.isEmpty()) BeumColors.Coral else BeumColors.baseGrayLightGray200
    Column {
        Box(
            modifier = Modifier.border(
                width = 1.dp, color = borderColor, shape = RoundedCornerShape(12.dp)
            ).clip(RoundedCornerShape(16.dp)).background(Color.White)
        ) {
            Column(
                modifier = Modifier.padding(start = 20.dp, end = 20.dp).fillMaxWidth()
            ) {
                Box(modifier = Modifier.height(20.dp).fillMaxWidth())
                Text(
                    text = title, fontWeight = FontWeight.Bold, style = TextStyle(
                        fontSize = BeumTypo.TypoScaleText200,
                        fontFamily = FontFamily(Font(Res.font.sf_pro)),
                        fontWeight = FontWeight(700),
                        color = Color(0xFF000000),
                    )
                )

                Box(modifier = Modifier.height(10.dp).fillMaxWidth())
                // KMP Compose에서는 backgroundColor만 커스텀

                Box(modifier = Modifier.fillMaxWidth()) {

                    when (title) {
                        "제목", "내 고민" -> {
                            val isMultiLine = title == "내 고민" // '내 고민'일 때만 여러 줄 허용하고 싶을 경우
                            BasicTextField(
                                value = content,
                                onValueChange = onTextChange,
                                singleLine = !isMultiLine,
                                maxLines = if (isMultiLine) 10 else 1,
                                interactionSource = interactionSource,
                                modifier = Modifier.fillMaxWidth().fillMaxHeight().defaultMinSize(minHeight = 50.dp)
                            )

                            if (content.isEmpty()) {
                                Text(
                                    text = placeholder, style = TextStyle(
                                        fontSize = BeumTypo.TypoScaleText150,
                                        lineHeight = BeumDimen.TypoLienheigtLineheight200,
                                        fontFamily = FontFamily(Font(Res.font.sf_pro)),
                                        fontWeight = FontWeight(500),
                                        color = BeumColors.baseGrayLightGray300,
                                    )
                                )
                            }
                        }

                        "태그" -> {
                            TagInput(tags = content) {
                                var tags = ""
                                it.forEach { str ->
                                    tags += str
                                }
                                onTextChange.invoke(tags)
                            }
                        }

                        "커뮤니티" -> {
                            Row {
                                val isDevil = content.toBoolean()
                                Row(
                                    modifier = Modifier.height(35.dp)
                                        .clip(RoundedCornerShape(82.dp)) // 먼저 모양을 깎고
                                        .background(color = if (isDevil) Color.White else Color.Black) // 그 위에 배경 색상
                                        .border(
                                            width = 1.dp,
                                            color = if (!isDevil) BeumColors.Black else BeumColors.baseGrayLightGray300,
                                            shape = RoundedCornerShape(82.dp)
                                        ).padding(start = 10.dp, end = 10.dp, top = 4.5.dp, bottom = 4.5.dp)
                                        .align(Alignment.CenterVertically)
                                        .clip(RoundedCornerShape(82.dp)).clickable {
                                            onTextChange(false.toString())
                                        },
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    if (!isDevil) {
                                        Image(
                                            modifier = Modifier.size(24.dp),
                                            painter = painterResource(Res.drawable.angel_abled),
                                            contentDescription = ""
                                        )
                                        Spacer(modifier = Modifier.width(5.dp))
                                    }
                                    Text(
                                        text = "천사", textAlign = TextAlign.Center, style = TextStyle(
                                            fontSize = 12.sp,
                                            lineHeight = 14.17.sp,
                                            fontFamily = FontFamily(Font(Res.font.sf_pro)),
                                            fontWeight = FontWeight(600),
                                            color = if (isDevil) BeumColors.baseGrayLightGray300 else BeumColors.White,
                                        )
                                    )
                                }
                                Spacer(modifier = Modifier.width(4.dp))
                                Row(
                                    modifier = Modifier.height(35.dp).defaultMinSize(minWidth = 55.dp)
                                        .clip(RoundedCornerShape(82.dp)) // 먼저 모양을 깎고
                                        .background(color = if (!isDevil) Color.White else Color.Black) // 그 위에 배경 색상
                                        .border(
                                            width = 1.dp,
                                            color = if (isDevil) BeumColors.Black else BeumColors.baseGrayLightGray300,
                                            shape = RoundedCornerShape(82.dp)
                                        ).padding(start = 10.dp, end = 10.dp, top = 4.5.dp, bottom = 4.5.dp)
                                        .clip(RoundedCornerShape(82.dp)).clickable {
                                            onTextChange(true.toString())
                                        }.align(Alignment.CenterVertically),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    if (isDevil) {
                                        Image(
                                            modifier = Modifier.size(24.dp),
                                            painter = painterResource(Res.drawable.devil_abled),
                                            contentDescription = ""
                                        )
                                        Spacer(modifier = Modifier.width(5.dp))
                                    }

                                    Text(
                                        text = "악마", textAlign = TextAlign.Center, style = TextStyle(
                                            fontSize = 12.sp,
                                            lineHeight = 14.17.sp,
                                            fontFamily = FontFamily(Font(Res.font.sf_pro)),
                                            fontWeight = FontWeight(600),
                                            color = if (!isDevil) BeumColors.baseGrayLightGray300 else BeumColors.White,
                                        )
                                    )
                                }
                            }
                        }

                        "카테고리" -> {
                            Row(
                                modifier = Modifier.clickable {
                                    click?.invoke()
                                }.border(
                                    width = 1.dp,
                                    color = BeumColors.baseGrayLightGray600,
                                    shape = RoundedCornerShape(size = 8.dp)
                                ).width(318.dp).height(48.dp).background(
                                    color = BeumColors.baseGrayLightGray75,
                                    shape = RoundedCornerShape(size = 8.dp)
                                ).padding(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 8.dp)
                                ,
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Text(
                                    text = if (content.isEmpty()) placeholder else content, style = TextStyle(
                                        fontSize = BeumTypo.TypoScaleText300,
                                        lineHeight = BeumDimen.TypoLienheigtLineheight200,
                                        fontFamily = FontFamily(Font(Res.font.sf_pro)),
                                        fontWeight = FontWeight(500),
                                        textAlign = TextAlign.Center,
                                        color = if (content.isEmpty()) BeumColors.baseGrayLightGray400 else BeumColors.baseGrayLightGray900,
                                    )
                                )

                                Spacer(modifier = Modifier.weight(1f))

                                Image(
                                    modifier = Modifier.width(8.dp).height(16.dp)
                                        .align(Alignment.CenterVertically),
                                    painter = painterResource(Res.drawable.icon_arrow_right_black),
                                    contentDescription = "image description"
                                )
                            }
                        }

                        "포인트" -> {
                            Row(
                                modifier = Modifier.padding(3.dp).width(318.dp).height(48.dp)
                                    .clickable {
                                        click?.invoke()
                                    }.background(
                                        color = BeumColors.baseGrayLightGray75,
                                        shape = RoundedCornerShape(size = 8.dp)
                                    ).padding(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 8.dp), verticalAlignment = Alignment.CenterVertically
                            ) {
                                if (content.toInt() > 0) {

                                    Text(
                                        text = content, style = TextStyle(
                                            fontSize = BeumTypo.TypoScaleText500,
                                            lineHeight = 32.8.sp,
                                            fontFamily = FontFamily(Font(Res.font.sf_pro)),
                                            fontWeight = FontWeight(500),
                                            color = BeumColors.baseGrayLightGray900,
                                            textAlign = TextAlign.Center
                                        )
                                    )

                                    Spacer(modifier = Modifier.width(4.dp))

                                    Image(
                                        modifier = Modifier.width(16.dp).height(16.dp).background(
                                            color = BeumColors.baseCoolGrayLightGray700,
                                            shape = RoundedCornerShape(size = 66.66668.dp)
                                        ).padding(
                                            start = 2.66667.dp,
                                            top = 2.66667.dp,
                                            end = 2.66667.dp,
                                            bottom = 2.66667.dp
                                        ).align(Alignment.CenterVertically),
                                        painter = painterResource(Res.drawable.heart),
                                        contentDescription = ""
                                    )
                                    Spacer(modifier = Modifier.weight(1f))
                                    Image(
                                        modifier = Modifier.width(20.dp).height(20.dp)
                                            .align(Alignment.CenterVertically).clickable {
                                                onTextChange.invoke("")
                                            },
                                        painter = painterResource(Res.drawable.ic_clear),
                                        contentDescription = ""
                                    )

                                } else {
                                    Text(
                                        text = "포인트 걸기", style = TextStyle(
                                            fontSize = BeumTypo.TypoScaleText300,
                                            lineHeight = BeumDimen.TypoLienheigtLineheight200,
                                            fontFamily = FontFamily(Font(Res.font.sf_pro)),
                                            fontWeight = FontWeight(500),
                                            color = BeumColors.baseGrayLightGray400,
                                        )
                                    )
                                    Spacer(modifier = Modifier.weight(1f))

                                    Image(
                                        modifier = Modifier.width(8.dp).height(16.dp)
                                            .align(Alignment.CenterVertically),
                                        painter = painterResource(Res.drawable.icon_arrow_right),
                                        colorFilter = ColorFilter.tint(color = BeumColors.Gray_400),
                                        contentDescription = "image description"
                                    )
                                }
                            }
                        }
                    }
                }
                Box(modifier = Modifier.height(22.dp).fillMaxWidth())
            }
            if (infoClick != null) {
                Image(
                    modifier = Modifier.align(Alignment.TopEnd).padding(20.dp).size(24.dp).clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        infoClick.invoke()
                    }, painter = painterResource(Res.drawable.img_info), contentDescription = ""
                )
            }
        }
        if (content.isEmpty() && !isFocused) {
            val errorText = when (title) {
                "제목" -> "제목을 적어주세요."
                "내 고민" -> "고민을 적어주세요."
                "태그" -> "태그를 적어주세요."
                "카테고리" -> "카테고리를 선택해주세요."
                else -> ""
            }
            if (errorText.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = errorText,
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(Res.font.sf_pro)),
                        fontWeight = FontWeight(400),
                        color = BeumColors.Coral,
                    )
                )
            }
        }
    }
}
