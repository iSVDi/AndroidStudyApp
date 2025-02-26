package com.example.project.modules.recipesList

import android.content.Intent
import android.gesture.Gesture
import androidx.navigation.compose.NavHost
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.navArgs
import androidx.room.Room
import coil3.compose.AsyncImage
import com.example.project.common.dataBase.RecipeAppDatabase
import com.example.project.modules.auth.viewModel.AuthViewModel
import com.example.project.modules.recipeDetails.RecipeDetailsActivity
import com.example.project.modules.recipesList.models.RecipeCell
import com.example.project.modules.recipesList.viewModel.RecipesViewModel
import com.example.project.modules.recipesList.viewModel.RecipesViewState
import com.example.project.ui.theme.ProjectTheme
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch


class RecipesListActivity : ComponentActivity() {
    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            RecipeAppDatabase::class.java,
            "RecipeAppDatabase"
        ).build()
    }

    @Suppress("UNCHECKED_CAST")
    private val viewModel by viewModels<RecipesViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return RecipesViewModel(db.recipeDao) as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        viewModel.onCreate()
        setContent {
            MainComposable()
        }
    }

    @Composable
    fun MainComposable() {
        ProjectTheme {
            Scaffold(
                topBar = {
                    Text("", modifier = Modifier.padding(top = 20.dp))
                }
            )
            { paddings ->
                when (viewModel._state.value) {
                    is RecipesViewState.Loading -> Loading()
                    is RecipesViewState.RecipesFetched -> RecipesScroll(
                        modifier = Modifier.padding(
                            paddings
                        )
                    )
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
        val recipes = (viewModel._state.value as RecipesViewState.RecipesFetched).recipes
        val coroutineScope = rememberCoroutineScope()
        val listState = rememberLazyListState()
        Box(contentAlignment = Alignment.BottomEnd) {

            LazyColumn(
                state = listState,
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = modifier
                    .padding(horizontal = 10.dp)
            ) {
                items(recipes) { recipe ->
                    RecipeCellComposable(recipe = recipe, modifier = Modifier)
                }
            }

            if (listState.lastScrolledForward) {
                FloatingActionButton(
                    modifier = Modifier.padding(bottom = 10.dp, end = 10.dp),
                    onClick = {
                        coroutineScope.launch {
                            listState.scrollToItem(0)
                        }
                    },
                ) {
                    Icon(Icons.Filled.Add, "Floating action button.")
                }
            }


        }

    }

    @Composable
    fun FloatingButton(listState: LazyListState) {

    }

    @Composable
    fun RecipeCellComposable(recipe: RecipeCell, modifier: Modifier) {
        Card(modifier = modifier
            .clickable {
                val navigate = Intent(
                    this@RecipesListActivity, RecipeDetailsActivity::class.java
                )
                navigate.putExtra("recipeId", recipe.id)
                startActivity(navigate)
            }
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                AsyncImage(
                    recipe.imageUrl,
                    contentDescription = null,
                    modifier = modifier
                        .weight(1f)
                        .size(130.dp)
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
