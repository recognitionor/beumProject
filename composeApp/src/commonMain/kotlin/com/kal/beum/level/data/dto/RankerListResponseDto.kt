package com.kal.beum.level.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class RankerListResponseDto(
    val page: Int,
    val size: Int,
    val sortedPointUserDtoList: List<SortedPointUserDto>
)
