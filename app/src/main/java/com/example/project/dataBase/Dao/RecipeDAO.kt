package com.example.project.dataBase.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.project.dataBase.Entities.RecipeEntity

@Dao
interface RecipeDAO {
    @Insert
    suspend fun insertRecipeDAO(recipeEntity: RecipeEntity)

    @Query("SELECT * FROM RecipeEntity")
    suspend fun getAllRecipes(): List<RecipeEntity>
}