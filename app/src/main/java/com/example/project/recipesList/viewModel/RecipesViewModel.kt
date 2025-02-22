package com.example.project.recipesList.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project.R
import com.example.project.recipesList.models.RecipeCell
import com.example.project.recipesList.network.RecipesApi
import kotlinx.coroutines.launch

class RecipesViewModel : ViewModel() {
    private val retrofit = RecipesApi.retrofitService
    val _state = mutableStateOf<RecipesViewState>(RecipesViewState.Loading)

    init {
        fetchRecipes()
    }

    private fun fetchRecipes() {
        viewModelScope.launch {
            val recipeResult = retrofit.getRecipes()
            
            val recipeCellList = recipeResult.recipes.map {
                val difficultyColor =
                    when(it.difficulty) {
                        "Easy" -> Color.Green
                        "Medium" -> Color.Red
                        else -> Color.Cyan
                    }
                RecipeCell(
                    id = it.id,
                    imageUrl = it.image,
                    title = it.name,
                    difficulty = it.difficulty,
                    difficultyColor = difficultyColor.copy(alpha = 0.6f),
                    tags = it.tags,
                )
            }
            _state.value = RecipesViewState.Success(recipeCellList)
        }
    }


}
