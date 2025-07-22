package com.kal.beum.myinfo.data.database

import androidx.room.Dao
import com.kal.beum.myinfo.domain.MyInfo

@Dao
interface MyInfoDao {
    fun getMyInfo(): MyInfo
}