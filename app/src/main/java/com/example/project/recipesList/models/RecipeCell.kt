package com.example.project.recipesList.models

import androidx.annotation.DrawableRes

data class RecipeCell(
    val id: Long,
    @DrawableRes val imageResourceId: Int,
    val title: String,
    val tags: List<String>
)