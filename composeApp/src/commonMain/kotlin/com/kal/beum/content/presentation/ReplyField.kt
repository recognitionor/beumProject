package com.kal.beum.content.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

@Composable
fun ReplyField(
    modifier: Modifier = Modifier,
    onSend: (String) -> Unit = {}
) {
    var text by remember { mutableStateOf("") }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFFF2F2F2), RoundedCornerShape(16.dp))
    ) {
        TextField(
            value = text,
            onValueChange = { text = it },
            placeholder = { Text("답글 남기기") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            // 배경/밑줄 제거
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            shape = RectangleShape, // 박스가 라운드를 가짐
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
            keyboardActions = KeyboardActions(
                onSend = {
                    if (text.isNotBlank()) {
                        onSend(text)
                        text = ""
                    }
                }
            ),
            trailingIcon = {
                if (text.isNotBlank()) {
                    IconButton(onClick = {
                        onSend(text)
                        text = ""
                    }) {
                        Icon(
                            imageVector = Icons.Default.Send, // 기본 비행기 아이콘
                            contentDescription = "보내기"
                        )
                    }
                }
            }
        )
    }
}
