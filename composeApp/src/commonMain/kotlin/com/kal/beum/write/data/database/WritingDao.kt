package com.kal.beum.write.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface WritingDao {

    // 전체 엔티티를 삽입하거나 교체 (기존 ID가 있으면 덮어쓰기)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplaceWriting(writing: WritingEntity)

    @Query("DELETE FROM WritingEntity WHERE id = :id")
    suspend fun deleteWritingById(id: Int): Int

    // 전체 엔티티를 업데이트 (기본 키를 기준으로)
    // 이 메서드를 사용하려면 먼저 엔티티를 조회하고 수정한 후 전달해야 합니다.
    @Update
    suspend fun updateWriting(writing: WritingEntity)

    // ID로 WritingEntity 조회
    @Query("SELECT * FROM WritingEntity WHERE id = :id")
    suspend fun getWritingById(id: Int): WritingEntity?

    // --- 개별 필드 업데이트 메서드 ---

    @Query("UPDATE WritingEntity SET title = :newTitle WHERE id = :id")
    suspend fun updateTitle(id: Int, newTitle: String)

    @Query("UPDATE WritingEntity SET content = :newContent WHERE id = :id")
    suspend fun updateContent(id: Int, newContent: String)

    @Query("UPDATE WritingEntity SET tags = :newTags WHERE id = :id")
    suspend fun updateTags(id: Int, newTags: String)

    @Query("UPDATE WritingEntity SET isDevil = :newIsDevil WHERE id = :id")
    suspend fun updateIsDevil(id: Int, newIsDevil: Boolean)

    @Query("UPDATE WritingEntity SET rewardPoint = :newRewardPoint WHERE id = :id")
    suspend fun updateRewardPoint(id: Int, newRewardPoint: Int)

    @Query("UPDATE WritingEntity SET categoryId = :newCategoryId, category = :newCategory WHERE id = :id")
    suspend fun updateCategoryInfo(id: Int, newCategoryId: Int, newCategory: String)
}
