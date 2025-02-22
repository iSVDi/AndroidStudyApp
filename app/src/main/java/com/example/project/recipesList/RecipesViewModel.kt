package com.example.project.recipesList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project.R
import com.example.project.recipesList.models.RecipeCell
import com.example.project.recipesList.network.RecipesApi
import kotlinx.coroutines.launch

class RecipesViewModel : ViewModel() {
    private val retrofit = RecipesApi.retrofitService

    init {
        fetchRecipes()
    }

    private fun fetchRecipes() {
        viewModelScope.launch {
            val recipeResult = retrofit.getRecipes()
            val recipeList = recipeResult.recipes
        }
    }



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
