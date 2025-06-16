package com.kal.beum.write.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import beumproject.composeapp.generated.resources.Res
import beumproject.composeapp.generated.resources.sf_pro
import com.kal.beum.core.presentation.BeumColors
import com.kal.beum.core.presentation.BeumDimen
import com.kal.beum.core.presentation.BeumTypo
import org.jetbrains.compose.resources.Font

@Composable
fun TagInput(
    onTagsChanged: (List<String>) -> Unit
) {
    var text by remember { mutableStateOf("") }
    var tagList by remember { mutableStateOf(listOf<String>()) }

    Row(verticalAlignment = Alignment.CenterVertically) {
        tagList.forEach {
            Text(
                text = it, style = TextStyle(
                    fontSize = 21.sp,
                    lineHeight = 33.sp,
                    fontFamily = FontFamily(Font(Res.font.sf_pro)),
                    fontWeight = FontWeight(400),
                    color = BeumColors.primaryPrimarySkyblue,
                )
            )
        }
        if (tagList.size < 3) {
            Box {
                BasicTextField(
                    value = text, onValueChange = { input ->
                        val delimiters = listOf(' ', ',', '\n')
                        if (input.isNotEmpty() && delimiters.any { input.last() == it }) {
                            // 구분자 앞의 텍스트(공백, 쉼표, 개행 제거)
                            val newTag = input.dropLast(1).trim()
                            tagList = if (!input.startsWith("#")) {
                                tagList + "#${newTag}"
                            } else {
                                tagList + newTag
                            }
                            if (newTag.isNotEmpty()) {
                                // 중복 방지 등도 추가 가능
                                onTagsChanged(tagList)
                            }
                            text = ""
                        } else {
                            text = input
                        }
                    }, singleLine = true, modifier = Modifier.fillMaxWidth()
                )

                if (text.isEmpty()) {
                    Text(
                        text = "#입력 후 키워드를 적어주세요.", style = TextStyle(
                            fontSize = BeumTypo.TypoScaleText150,
                            lineHeight = BeumDimen.TypoLienheigtLineheight200,
                            fontFamily = FontFamily(Font(Res.font.sf_pro)),
                            fontWeight = FontWeight(500),
                            color = BeumColors.baseGrayLightGray500,
                        )
                    )
                }
            }
        }
    }
}
