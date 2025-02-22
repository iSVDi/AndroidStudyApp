package com.example.project.recipesList

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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.project.recipesList.models.RecipeCell
import com.example.project.recipesList.viewModel.RecipesViewModel
import com.example.project.recipesList.viewModel.RecipesViewState
import com.example.project.ui.theme.ProjectTheme

class RecipesListActivity : ComponentActivity() {
    private val viewModel: RecipesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainComposable()
        }
    }

    @Composable
    fun MainComposable() {
        ProjectTheme {
            Scaffold(
                topBar = {
                    Text("Top bar", modifier = Modifier.padding(top = 20.dp))
                }
            )
            { paddings ->
                when (viewModel._state.value) {
                    is RecipesViewState.Success -> RecipesScroll(modifier = Modifier.padding(paddings))
                    is RecipesViewState.Loading -> Loading()
                }
            }
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
    fun RecipesScroll(modifier: Modifier) {
        val recipes = (viewModel._state.value as RecipesViewState.Success).recipes
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = modifier
                .padding(horizontal = 10.dp)

        ) {
            items(recipes) { recipe ->
                RecipeCellComposable(recipe = recipe, modifier = Modifier)
            }
        }
    }

    @Composable
    fun RecipeCellComposable(recipe: RecipeCell, modifier: Modifier) {
        Card(modifier = modifier) {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                AsyncImage(
                    recipe.imageUrl,
                    contentDescription = null,
                    modifier = modifier
                        .weight(1f)
                )
                Column(
                    modifier = modifier.weight(2f)
                ) {
                    Text(
                        recipe.title,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        recipe.difficulty,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = recipe.difficultyColor,
                    )

                    Text(
                        recipe.tags.joinToString(separator = ", "),
                        fontSize = 12.sp,
                        fontStyle = FontStyle.Italic
                    )

                }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun Preview() {
        MainComposable()
    }
}
