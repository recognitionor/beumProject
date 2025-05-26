package com.kal.beum.community.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class CategoryDto(val id: Int = -1, val category: String = "")
