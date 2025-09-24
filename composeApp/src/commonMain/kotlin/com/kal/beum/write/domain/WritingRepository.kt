package com.kal.beum.write.domain

import com.kal.beum.core.domain.DataError
import com.kal.beum.core.domain.Result

interface WritingRepository {
    suspend fun submitWriting(writingSubmitRequest: WritingInfoRequest): Result<Boolean, DataError.Remote>

    suspend fun saveTempWritingTitle(title: String): Result<Boolean, DataError.Local>
    suspend fun saveTempWritingContent(content: String): Result<Boolean, DataError.Local>
    suspend fun saveTempWritingTags(tags: String): Result<Boolean, DataError.Local>
    suspend fun saveTempWritingIsDevil(isDevil: Boolean): Result<Boolean, DataError.Local>
    suspend fun saveTempWritingRewardPoint(rewardPoint: Int): Result<Boolean, DataError.Local>
    suspend fun saveTempWritingCategory(writingCategory: WritingCategory): Result<Boolean, DataError.Local>


}