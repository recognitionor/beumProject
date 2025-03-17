package com.kal.beum.home.data.network

import com.kal.beum.core.domain.DataError
import com.kal.beum.core.domain.Result
import com.kal.beum.home.data.dto.HomeCommentDto
import io.ktor.client.HttpClient

class KtorRemoteHomeDataSource(
    private val httpClient: HttpClient
) : RemoteHomeDataSource {
    override suspend fun getHomeCommentList(isDevil: Boolean): Result<List<HomeCommentDto>, DataError.Remote> {
        TODO("Not yet implemented")
    }

}