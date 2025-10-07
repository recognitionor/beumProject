package com.kal.beum.myinfo.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class MyBoardInfoDto(
    val boardList: List<MyBoardDto> = emptyList(),
    val nickname: String,
    val page: Int,
    val point: Int,
    val size: Int
)