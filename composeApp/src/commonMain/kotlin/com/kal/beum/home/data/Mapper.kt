package com.kal.beum.home.data

import com.kal.beum.home.data.dto.HomeCommentDto
import com.kal.beum.home.domain.HomeData

fun HomeCommentDto.toHomeData(): HomeData {
    return HomeData(
        id = this.id, concernMsg = this.comment
    )
}