package com.example.project.auth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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
import com.example.project.R
import com.example.project.ui.theme.ProjectTheme

class AuthActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Content(Modifier.fillMaxSize())
        }
    }

    @Composable
    fun Content(modifier: Modifier) {
        ProjectTheme {
            Surface {
                AuthSection(modifier)
            }
        }
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
        val shouldPresentAlert = remember { mutableStateOf(false) }
        Button(onClick = {
            TODO()
//            val userData = "$userName,$password"
//            if (users.value.find {
//                    it.userName == userData && it.password == password
//                } != null) {
//                val navigate = Intent(
//                    this@AuthActivity, RecipesListActivity::class.java
//                )
//                startActivity(navigate)
//            } else {
//                shouldPresentAlert.value = true
//            }
        }) {
            Text("Login")
        }
        when {
            shouldPresentAlert.value -> {
                FailedLoginAlert(shouldPresentAlert)
            }
        }
    }


    @Composable
    fun FailedLoginAlert(state: MutableState<Boolean>) {
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
                    state.value = false
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



