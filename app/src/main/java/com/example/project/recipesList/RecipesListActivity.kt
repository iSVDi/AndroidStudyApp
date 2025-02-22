package com.example.project.recipesList

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.project.recipesList.models.RecipeCell
import com.example.project.ui.theme.ProjectTheme

class RecipesListActivity : ComponentActivity() {
    private var viewModel = RecipesViewModel()

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
                topBar = { Text("Top bar") }
            )
            { paddings ->
                RecipesScroll(
                    viewModel.getRecipes(),
                    modifier = Modifier.padding(paddings)
                )
            }
        }
    }


    @Composable
    fun RecipesScroll(recipes: List<RecipeCell>, modifier: Modifier) {
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
                Image(
                    painter = painterResource(recipe.imageResourceId),
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
