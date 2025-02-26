package com.example.project.modules.recipesList.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project.common.dataBase.recipe.RecipeDAO
import com.example.project.common.network.Recipe
import com.example.project.common.network.RecipesApi
import kotlinx.coroutines.launch

class RecipesViewModel(private val recipeDAO: RecipeDAO) : ViewModel() {
    private val retrofit = RecipesApi.retrofitService
    val _state = mutableStateOf<RecipesViewState>(RecipesViewState.Loading)

    fun onCreate() {
        viewModelScope.launch {
            if (recipeDAO.getAllRecipes().isEmpty()) {
                fetchRecipesFromAPI()
            } else {
                fetchRecipesFromDB()
            }
        }
    }

    private suspend fun fetchRecipesFromAPI() {
        val recipeResult = retrofit.getRecipes()

        saveRecipeToDB(recipeResult.recipes)
        val recipeCellList = recipeResult.recipes.map {
            it.mapToRecipeCell()
        }
        _state.value = RecipesViewState.RecipesFetched(recipeCellList)
    }

    private suspend fun fetchRecipesFromDB() {
        val recipesCell = recipeDAO.getAllRecipes().map { it.mapToRecipeCell() }
        _state.value = RecipesViewState.RecipesFetched(recipesCell)
    }

    private suspend fun saveRecipeToDB(recipes: List<Recipe>) {
        recipeDAO.deleteAllRecipes()
        val recipeEntities = recipes.map { it.mapToRecipeEntity() }
        recipeEntities.forEach {
            recipeDAO.insert(it)
        }
    }

}
