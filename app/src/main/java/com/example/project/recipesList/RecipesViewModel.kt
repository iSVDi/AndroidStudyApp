package com.example.project.recipesList

import com.example.project.R
import com.example.project.recipesList.models.RecipeCell

class RecipesViewModel() {
    fun getRecipes(): List<RecipeCell> {
        return List(20) { index ->
            RecipeCell(
                id = 0,
                imageResourceId = R.drawable.recipeplugimage,
                title = "Classic Margherita Pizza $index",
                tags = listOf("Pizza", "Italian")
            )
        }
    }
}






