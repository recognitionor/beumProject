package com.kal.beum.notice.data.network

import com.kal.beum.core.domain.DataError
import com.kal.beum.core.domain.Result
import com.kal.beum.notice.data.dto.NoticeCategoryDto
import com.kal.beum.notice.data.dto.NoticeMapDto

interface RemoteNoticeDataSource {
    suspend fun getNoticeMap(): Result<NoticeMapDto, DataError.Remote>
}