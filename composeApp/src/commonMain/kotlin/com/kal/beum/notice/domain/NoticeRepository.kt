package com.kal.beum.notice.domain

import com.kal.beum.core.domain.DataError
import com.kal.beum.core.domain.Result

interface NoticeRepository {
    suspend fun getNoticeMap(): Result<NoticeMap, DataError.Remote>
}