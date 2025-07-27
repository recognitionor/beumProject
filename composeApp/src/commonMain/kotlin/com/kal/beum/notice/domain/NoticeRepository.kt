package com.kal.beum.notice.domain

import com.kal.beum.community.domain.Category
import com.kal.beum.core.domain.DataError
import com.kal.beum.core.domain.Result

interface NoticeRepository {
    suspend fun getCategoryList(): Result<List<NoticeCategory>, DataError.Remote>
    suspend fun getNoticeList(): Result<List<NoticeData>, DataError.Remote>
}