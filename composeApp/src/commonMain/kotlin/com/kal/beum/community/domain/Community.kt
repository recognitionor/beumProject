package com.kal.beum.community.domain

data class Community(
    val page: Int, val size: Int, val boardList: List<CommunityItem>
)
