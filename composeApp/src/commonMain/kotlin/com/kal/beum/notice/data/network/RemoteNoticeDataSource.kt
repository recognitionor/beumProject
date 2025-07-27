package com.kal.beum.notice.data.network

import com.kal.beum.core.domain.DataError
import com.kal.beum.core.domain.Result
import com.kal.beum.notice.data.dto.NoticeCategoryDto
import com.kal.beum.notice.data.dto.NoticeDto

interface RemoteNoticeDataSource {
    suspend fun getCategoryList(): Result<List<NoticeCategoryDto>, DataError.Remote>
    suspend fun getNoticeList(): Result<List<NoticeDto>, DataError.Remote>
}