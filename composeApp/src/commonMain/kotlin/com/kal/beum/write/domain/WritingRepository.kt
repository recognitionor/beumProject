package com.kal.beum.write.domain

import com.kal.beum.core.domain.DataError
import com.kal.beum.core.domain.Result

interface WritingRepository {
    suspend fun submitWriting(writingSubmitRequest: WritingInfoRequest): Result<Boolean, DataError.Remote>
}