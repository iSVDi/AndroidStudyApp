package com.example.project.common.dataBase

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [UserDataEntity::class], version = 1)
abstract class RecipeAppDatabase : RoomDatabase() {
    abstract val userDataDao: UserDataDao
}