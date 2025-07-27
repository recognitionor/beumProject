package com.kal.beum.notice.data

import com.kal.beum.core.domain.DataError
import com.kal.beum.core.domain.Result
import com.kal.beum.core.domain.map
import com.kal.beum.notice.data.network.RemoteNoticeDataSource
import com.kal.beum.notice.domain.NoticeCategory
import com.kal.beum.notice.domain.NoticeData
import com.kal.beum.notice.domain.NoticeRepository

class DefaultNoticeRepository(
    private val remoteNoticeDataSource: RemoteNoticeDataSource
) : NoticeRepository {
    override suspend fun getCategoryList(): Result<List<NoticeCategory>, DataError.Remote> {
        return remoteNoticeDataSource.getCategoryList().let { result ->
            result.map { categoryDto ->
                categoryDto.map { it.toNoticeCategoryData() }
            }
        }
    }

    override suspend fun getNoticeList(): Result<List<NoticeData>, DataError.Remote> {
        return remoteNoticeDataSource.getNoticeList().let { result ->
            result.map { noticeDto ->
                noticeDto.map { it.toNoticeData() }
            }
        }
    }
}