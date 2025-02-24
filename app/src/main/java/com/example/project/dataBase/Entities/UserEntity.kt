package com.example.project.dataBase.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val userName: String,
    val password: String
)
