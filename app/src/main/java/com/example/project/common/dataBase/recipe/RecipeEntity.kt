package com.example.project.common.dataBase.recipe

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.project.common.dataBase.StringListConverter
import com.example.project.modules.recipesList.models.RecipeCell


@Entity(tableName = "recipe")
data class RecipeEntity(
    @PrimaryKey val id: Long,
    val name: String,
    @TypeConverters(StringListConverter::class) val ingredients: List<String>,
    @TypeConverters(StringListConverter::class) val instructions: List<String>,
    val prepTimeMinutes: Long,
    val cookTimeMinutes: Long,
    val servings: Long,
    val difficulty: String,
    val cuisine: String,
    val caloriesPerServing: Long,
    @TypeConverters(StringListConverter::class) val tags: List<String>,
    val userID: Long,
    val image: String,
    val rating: Double,
    val reviewCount: Long,
    @TypeConverters(StringListConverter::class) val mealType: List<String>
) {

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
