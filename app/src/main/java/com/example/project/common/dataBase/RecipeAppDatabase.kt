package com.example.project.common.dataBase

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.project.common.dataBase.recipe.RecipeDAO
import com.example.project.common.dataBase.recipe.RecipeEntity
import com.example.project.common.dataBase.user.UserDataDao
import com.example.project.common.dataBase.user.UserDataEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Database(entities = [UserDataEntity::class, RecipeEntity::class], version = 1)
@TypeConverters(StringListConverter::class)
abstract class RecipeAppDatabase : RoomDatabase() {
    abstract val userDataDao: UserDataDao
    abstract val recipeDao: RecipeDAO
}

class StringListConverter {
    @TypeConverter
    fun fromString(value: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun toString(list: List<String>): String {
        return Gson().toJson(list)
    }
}