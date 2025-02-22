package com.example.project.recipesList.viewModel

import com.example.project.recipesList.models.RecipeCell

sealed class RecipesViewState {
    object Loading: RecipesViewState()
    data class Success(val recipes: List<RecipeCell>): RecipesViewState()
}