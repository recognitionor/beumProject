package com.kal.beum.write.data

import kotlinx.serialization.Serializable

@Serializable
data class CategoryGroupDto(val id: Int, val groupName: String, val category: String)
