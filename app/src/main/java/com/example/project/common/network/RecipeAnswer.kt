package com.example.project.common.network


import androidx.compose.ui.graphics.Color
import com.example.project.common.dataBase.recipe.RecipeEntity
import com.example.project.modules.recipesList.models.RecipeCell

data class RecipeAnswer(
    val recipes: List<Recipe>,
    val total: Long,
    val skip: Long,
    val limit: Long
)

data class Recipe(
    val id: Long,
    val name: String,
    val ingredients: List<String>,
    val instructions: List<String>,
    val prepTimeMinutes: Long,
    val cookTimeMinutes: Long,
    val servings: Long,
    val difficulty: String,
    val cuisine: String,
    val caloriesPerServing: Long,
    val tags: List<String>,
    val userID: Long,
    val image: String,
    val rating: Double,
    val reviewCount: Long,
    val mealType: List<String>
) {
    fun mapToRecipeEntity(): RecipeEntity {
        return RecipeEntity(
            id = this.id,
            name = this.name,
            ingredients = this.ingredients,
            instructions = this.instructions,
            prepTimeMinutes = this.prepTimeMinutes,
            cookTimeMinutes = this.cookTimeMinutes,
            servings = this.servings,
            difficulty = this.difficulty,
            cuisine = this.cuisine,
            caloriesPerServing = this.caloriesPerServing,
            tags = this.tags,
            userID = this.userID,
            image = this.image,
            rating = this.rating,
            reviewCount = this.reviewCount,
            mealType = this.mealType
        )
    }

    fun mapToRecipeCell(): RecipeCell {
        val difficultyColor =
            when(this.difficulty) {
                "Easy" -> Color(61, 142,74, 255)
                "Medium" -> Color(222, 142,74, 255)
                else -> Color.Cyan
            }
        return RecipeCell(
            id = this.id,
            imageUrl = this.image,
            title = this.name,
            difficulty = this.difficulty,
            difficultyColor = difficultyColor,
            tags = this.tags,
        )
    }
}
