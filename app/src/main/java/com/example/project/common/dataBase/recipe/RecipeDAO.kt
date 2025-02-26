package com.example.project.common.dataBase.recipe

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RecipeDAO {
    @Insert
    suspend fun insert(vararg recipes: RecipeEntity)

    @Query("SELECT * FROM recipe")
    suspend fun getAllRecipes(): List<RecipeEntity>

    @Query("SELECT * FROM recipe WHERE id = :recipeId")
    suspend fun getRecipeById(recipeId: Long): RecipeEntity

    @Query("DELETE FROM recipe")
    suspend fun deleteAllRecipes()
}