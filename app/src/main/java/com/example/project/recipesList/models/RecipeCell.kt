package com.example.project.recipesList.models

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color

data class RecipeCell(
    val id: Long,
    val imageUrl: String,
    val title: String,
    val difficulty: String,
    val difficultyColor: Color,
    val tags: List<String>
)