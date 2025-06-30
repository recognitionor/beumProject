package com.kal.beum.content.data.repository

import com.kal.beum.content.data.network.RemoteContentDataSource
import com.kal.beum.content.data.toContentDetail
import com.kal.beum.content.domain.ContentDetail
import com.kal.beum.content.domain.ContentsRepository
import com.kal.beum.core.domain.DataError
import com.kal.beum.core.domain.Result
import com.kal.beum.core.domain.map

class DefaultContentDetailRepository(private val remoteContentDataSource: RemoteContentDataSource) :
    ContentsRepository {
    override suspend fun getContentInfo(
        id: Int
    ): Result<ContentDetail, DataError.Remote> {
        return remoteContentDataSource.getContentDetail(id).map { it.toContentDetail() }
    }
}