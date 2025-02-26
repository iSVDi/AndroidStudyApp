package com.example.project.modules.recipeDetails

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project.common.dataBase.recipe.RecipeDAO
import com.example.project.common.dataBase.recipe.RecipeEntity
import kotlinx.coroutines.launch

class RecipeDetailsViewModel(private val recipeDao: RecipeDAO) : ViewModel() {
    val state = mutableStateOf<RecipeDetailsViewState>(RecipeDetailsViewState.Loading)

    fun onCreate(recipeId: Long?) {

        if (recipeId != null) {
            viewModelScope.launch {
                val recipe = recipeDao.getRecipeById(recipeId)
                state.value = RecipeDetailsViewState.Recipe(recipe)
            }
        } else {
            TODO("Handle error")
        }


    }


}

sealed class RecipeDetailsViewState {
    object Loading : RecipeDetailsViewState()
    data class Recipe(val recipe: RecipeEntity) : RecipeDetailsViewState();

}
