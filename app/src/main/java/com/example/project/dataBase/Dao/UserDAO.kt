package com.example.project.dataBase.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.project.dataBase.Entities.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDAO {
    @Insert
    suspend fun insertUserData(userEntity: UserEntity)

    @Query("SELECT * FROM UserEntity")
    fun getUsers(): Flow<List<UserEntity>>

}