package com.kal.beum.community.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryMapDto(
    @SerialName("categoryMap") val categoryMap: Map<String, List<CategoryDto>>
)
