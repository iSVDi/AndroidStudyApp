package com.example.project.dataBase

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.project.dataBase.Dao.RecipeDAO
import com.example.project.dataBase.Dao.UserDAO

import com.example.project.dataBase.Entities.RecipeEntity
import com.example.project.dataBase.Entities.UserEntity

@Database(entities = [UserEntity::class, RecipeEntity::class], version = 1)
abstract class RecipeDataBase() : RoomDatabase() {

    abstract val userDao: UserDAO
    abstract val recipeDao: RecipeDAO

    companion object {
        fun createDataBase(context: Context): RecipeDataBase {
            return Room.databaseBuilder(context, RecipeDataBase::class.java, "RecipeDataBase")
                .build()
        }
    }
}