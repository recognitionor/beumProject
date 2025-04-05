package com.kal.beum.home.data.network

import com.kal.beum.core.domain.DataError
import com.kal.beum.core.domain.Result
import com.kal.beum.home.data.dto.HomeCommentDto

class MockHomeDataSource(
) : RemoteHomeDataSource {
    override suspend fun getHomeCommentList(isDevil: Boolean): Result<List<HomeCommentDto>, DataError.Remote> {
        println("mock-getHomeCommentList")
        return Result.Success(
            listOf(
                HomeCommentDto(1, "show me the money!"),
                HomeCommentDto(2, "동해물과"),
                HomeCommentDto(3, "아리"),
                HomeCommentDto(4, "가또오부시맨부시맨빵은 아웃백"),
                HomeCommentDto(5, "얍!!!"),
                HomeCommentDto(6, "로운이의 시간표 월요일, 화요일, 수요일, 목요일, 금요일, 토요일, 일요일"),
                HomeCommentDto(7, "난늘 코딩이야 다른짓하지말고 코딩이야")
            )
        )
    }
}