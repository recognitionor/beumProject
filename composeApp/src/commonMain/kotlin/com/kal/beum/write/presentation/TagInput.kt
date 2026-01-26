package com.kal.beum.write.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.sp
import beumproject.composeapp.generated.resources.Res
import beumproject.composeapp.generated.resources.sf_pro
import com.kal.beum.core.presentation.BeumColors
import com.kal.beum.core.presentation.BeumDimen
import com.kal.beum.core.presentation.BeumTypo
import org.jetbrains.compose.resources.Font

@Composable
fun TagInput(
    tags: String = "", onTagsChanged: (List<String>) -> Unit
) {
    var text by remember { mutableStateOf("") }
    var tagList by remember(tags) { // key로 tagsStringFromParent를 주면, 이 값이 바뀔 때마다 재초기화
        val initialList =
            tags.split("#").filter { it.isNotBlank() }.map { it.trim() } // 각 태그 앞뒤 공백 제거
                .filter { it.isNotEmpty() } // 공백만 있던 태그 제거
                .map { if (!it.startsWith("#")) "#$it" else it } // '#' 일관성 유지
        mutableStateOf(initialList)
    }
    var prevText by remember { mutableStateOf("") }
    Row(verticalAlignment = Alignment.CenterVertically) {
        tagList.forEach {
            Text(
                text = it, style = TextStyle(
                    fontSize = BeumTypo.TypoScaleText150,
                    lineHeight = BeumDimen.TypoLienheigtLineheight200,
                    fontFamily = FontFamily(Font(Res.font.sf_pro)),
                    fontWeight = FontWeight(400),
                    color = BeumColors.angelSkyblue,
                )
            )
        }
        Box {
            BasicTextField(
                value = text, onValueChange = { input ->
                val delimiters = listOf(' ', ',', '\n')
                if (input.isNotEmpty() && delimiters.any { input.last() == it }) {
                    // 구분자 앞의 텍스트(공백, 쉼표, 개행 제거)
                    val newTag = input.dropLast(1).trim()

                    tagList = if (!input.startsWith("#")) {
                        if (tagList.contains("#${newTag}")) {
                            tagList
                        } else {
                            tagList + "#${newTag}"
                        }
                    } else {
                        if (tagList.contains(newTag)) {
                            tagList
                        } else {
                            tagList + newTag
                        }
                    }
                    if (newTag.isNotEmpty()) {
                        // 중복 방지 등도 추가 가능
                        onTagsChanged(tagList)
                    }
                    text = ""
                } else {
                    text = if (tagList.size >= 3) prevText else input
                }
                prevText = text
            }, singleLine = true, modifier = Modifier.fillMaxWidth().onKeyEvent { keyEvent ->
                if (keyEvent.key == Key.Backspace) println("back key : " + tagList.size)
                if (tagList.isNotEmpty()) {
                    tagList = tagList - tagList[tagList.lastIndex]
                }
                false
            }, keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ), keyboardActions = KeyboardActions(onDone = {
                println("backKey")
                val newTag = text.trim()
                if (newTag.isNotEmpty()) {
                    tagList = if (!newTag.startsWith("#")) {
                        tagList + "#$newTag"
                    } else {
                        tagList + newTag
                    }
                    onTagsChanged(tagList)
                    text = ""
                }
            })
            )

            if (text.isEmpty() && tagList.isEmpty()) {
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
