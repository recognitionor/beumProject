package com.kal.beum.community.presentation

import com.kal.beum.content.domain.ContentDetail

data class ContentDetailState(
    val contentDetail: ContentDetail? = null,
    val isProgress: Boolean = false,
    val isError: Boolean = false,
)