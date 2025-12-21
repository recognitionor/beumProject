package com.kal.beum.notice.data

import com.kal.beum.core.domain.DataError
import com.kal.beum.core.domain.Result
import com.kal.beum.core.domain.map
import com.kal.beum.notice.data.dto.NoticeMapDto
import com.kal.beum.notice.data.network.RemoteNoticeDataSource
import com.kal.beum.notice.domain.NoticeCategory
import com.kal.beum.notice.domain.NoticeMap
import com.kal.beum.notice.domain.NoticeRepository

class DefaultNoticeRepository(
    private val remoteNoticeDataSource: RemoteNoticeDataSource
) : NoticeRepository {

    override suspend fun getNoticeMap(): Result<NoticeMap, DataError.Remote> {
        return remoteNoticeDataSource.getNoticeMap().map { noticeMapDto ->
            noticeMapDto.toNoticeMap()
        }
    }
}