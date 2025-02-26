package com.example.project.modules.recipeDetails

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.Group
import androidx.compose.ui.input.pointer.PointerEventType.Companion.Scroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.data.Group
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import coil3.compose.AsyncImage
import com.example.project.common.dataBase.RecipeAppDatabase
import com.example.project.common.dataBase.recipe.RecipeEntity
import com.example.project.modules.recipesList.viewModel.RecipesViewModel
import com.example.project.modules.recipesList.viewModel.RecipesViewState
import com.example.project.ui.theme.ProjectTheme

class RecipeDetailsActivity : ComponentActivity() {
    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            RecipeAppDatabase::class.java,
            "RecipeAppDatabase"
        ).build()
    }

    @Suppress("UNCHECKED_CAST")
    private val viewModel by viewModels<RecipeDetailsViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return RecipeDetailsViewModel(db.recipeDao) as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val recipeId = intent.extras?.getLong("recipeId")
        viewModel.onCreate(recipeId)
        setContent {
            ProjectTheme {
                Scaffold(
                    topBar = {
                        Text("", modifier = Modifier.padding(top = 10.dp))
                    }
                ) { padding ->
                    Content(
                        Modifier
                            .padding(padding)
                    )
                }
            }
        }
    }

    @Composable
    fun Content(modifier: Modifier) {
        when (viewModel.state.value) {
            is RecipeDetailsViewState.Loading -> Loading()
            is RecipeDetailsViewState.Recipe -> Recipe(
                (viewModel.state.value as RecipeDetailsViewState.Recipe).recipe,
                modifier
            )
        }
    }

    @Composable
    fun Loading() {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator(
                modifier = Modifier.width(64.dp),
                color = MaterialTheme.colorScheme.secondary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant
            )
        }
    }

    @Composable
    fun Recipe(recipe: RecipeEntity, modifier: Modifier) {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
        ) {
            items(listOf(recipe)) {
                RecipeItem(it, modifier = modifier)
            }
        }
    }

    @Composable
    fun RecipeItem(recipe: RecipeEntity, modifier: Modifier) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = modifier
        ) {
            AsyncImage(recipe.image, contentDescription = null, modifier = Modifier.fillMaxWidth())
            Text(
                recipe.name,
                fontSize = 28.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Divider()
            SectionWithTexts("Tags", recipe.tags)
            Divider()
            SectionTitleWithValue("Rating", value = "${recipe.rating}")
            Divider()
            SectionTitleWithValue("Preparation Time (Min)", value = "${recipe.prepTimeMinutes}")
            Divider()
            SectionTitleWithValue("Cook Time (Min)", value = "${recipe.cookTimeMinutes}")
            Divider()
            SectionTitleWithValue("Servings", value = "${recipe.servings}")
            Divider()
            SectionTitleWithValue("Difficulty", value = recipe.difficulty)
            Divider()
            SectionTitleWithValue("Cuisine", value = recipe.cuisine)
            Divider()
            SectionTitleWithValue("Calories per serving", value = "${recipe.caloriesPerServing}")
            Divider()
            SectionTitleWithValue("Difficulty", value = recipe.difficulty)
            Divider()
            SectionWithTexts("Ingredients", texts = recipe.ingredients)
            Divider()
            SectionWithTexts("Instruction", texts = recipe.instructions)
            Divider()
        }
    }

    @Composable
    fun SectionTitleWithValue(title: String, value: String) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
        ) {
            Text(
                title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(value)
        }
    }

    @Composable
    fun SectionWithTexts(title: String, texts: List<String>) {
        Column {
            Text(
                title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 10.dp)
            )

            texts.forEach {
                Text("- $it", modifier = Modifier.padding(start = 5.dp))
            }
        }
    }

    @Composable
    fun Divider() {
        HorizontalDivider(thickness = 2.dp)
    }


}