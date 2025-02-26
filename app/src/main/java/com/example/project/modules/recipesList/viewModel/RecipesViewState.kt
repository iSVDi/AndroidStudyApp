package com.example.project.modules.recipesList.viewModel

import com.example.project.modules.recipesList.models.RecipeCell

sealed class RecipesViewState {
    object Loading: RecipesViewState()
    data class Success(val recipes: List<RecipeCell>): RecipesViewState()
}