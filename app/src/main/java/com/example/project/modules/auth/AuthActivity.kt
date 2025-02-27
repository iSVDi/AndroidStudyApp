package com.example.project.modules.auth

import android.content.Intent
import android.content.res.Resources.Theme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.project.R
import com.example.project.modules.auth.viewModel.AuthViewModel
import com.example.project.modules.auth.viewModel.AuthViewState.*
import com.example.project.common.dataBase.RecipeAppDatabase
import com.example.project.common.SharedPreferencesHelper
import com.example.project.modules.recipesList.RecipesListActivity
import com.example.project.ui.theme.ProjectTheme
import kotlinx.coroutines.delay


class AuthActivity : ComponentActivity() {

    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            RecipeAppDatabase::class.java,
            "RecipeAppDatabase"
        ).build()
    }

    @Suppress("UNCHECKED_CAST")
    private val viewModel by viewModels<AuthViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return AuthViewModel(db.userDataDao) as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        viewModel.onCreateHandle()
        setContent {

            if (viewModel.isNeedAnimated.value) {
                SplashScreenApp {
                    Content(Modifier.fillMaxSize())
                }
                viewModel.isNeedAnimated.value = false
            } else {
                Content(Modifier.fillMaxSize())
            }

        }
    }

    @Composable
    fun SplashScreenApp(content: @Composable () -> Unit) {
        var showSplash by remember { mutableStateOf(true) }

        // Handle splash screen visibility
        LaunchedEffect(Unit) {
            delay(2000) // Display splash screen for 2 seconds
            showSplash = false
        }

        if (showSplash) {
            SplashScreenContent()
        } else {
            content()
        }
    }

    @Composable
    fun SplashScreenContent() {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(painterResource(R.drawable.recipe), contentDescription = null)
                    Text(
                        text = "Recipes",
                        style = MaterialTheme.typography.displayMedium
                    )
                }
            }
        }
    }

    @Composable
    fun Content(modifier: Modifier) {
        ProjectTheme {
            Surface {
                Box {
                    val sharedPreferencesHelper =
                        remember { SharedPreferencesHelper(this@AuthActivity) }
                    AuthSection(modifier)
                    when (viewModel.state.value) {
                        ErrorWhileLogin -> FailedLoginAlert()
                        InitState -> {
                            if (sharedPreferencesHelper.getIsFirstLaunched()) {
                                toRecipeScreenIntension()
                            }
                        }

                        SuccessLogin -> {
                            sharedPreferencesHelper.updateIsFirstLaunched()
                            toRecipeScreenIntension()
                        }
                    }

                }
            }
        }
    }


    fun toRecipeScreenIntension() {
        val navigate = Intent(
            this@AuthActivity, RecipesListActivity::class.java
        )
        startActivity(navigate)
    }


    @Composable
    fun AuthSection(modifier: Modifier) {
        var userName by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
        ) {

            Image(
                painter = painterResource(R.drawable.recipe),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.size(240.dp)
            )

            TextField(value = userName, onValueChange = { userName = it }, label = {
                Text("User name")
            }, modifier = Modifier.padding(bottom = 10.dp)
            )

            TextField(value = password, onValueChange = { password = it }, label = {
                Text("Password")
            }, modifier = Modifier.padding(bottom = 12.dp)
            )

            LoginButton(userName, password)

        }

    }

    @Composable
    fun LoginButton(
        userName: String, password: String,
    ) {
        Button(onClick = {
            viewModel.checkUserBy(userName, password)
        }) {
            Text("Login")
        }
    }


    @Composable
    fun FailedLoginAlert() {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(0.5f))
        ) {
            AlertDialog(icon = {
                Icon(imageVector = Icons.Rounded.Warning, contentDescription = null)
            }, title = {
                Text(text = "Login failed")
            }, text = {
                Text(text = "User with such userName and password not found")
            }, onDismissRequest = {}, confirmButton = {
                TextButton(onClick = {
                    viewModel.handleAlertOkAction()
                }) { Text("Ok") }
            }

            )
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun Preview() {
        Content(Modifier.fillMaxSize())
    }

}



