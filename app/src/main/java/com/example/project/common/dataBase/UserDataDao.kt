package com.example.project.common.dataBase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDataDao {
    @Query("SELECT * FROM user_data")
    suspend fun getAll(): List<UserDataEntity>

    @Insert
    suspend fun insertAllUserDatas(vararg userDatas: UserDataEntity)
}