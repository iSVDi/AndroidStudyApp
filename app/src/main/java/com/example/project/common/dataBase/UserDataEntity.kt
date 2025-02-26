package com.example.project.common.dataBase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_data")
data class UserDataEntity(
    @PrimaryKey val id: Int? = null,
    val userName: String,
    val password: String
)