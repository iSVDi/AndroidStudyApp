package com.example.project


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.AccountCircle
import androidx.compose.material.icons.sharp.Call
import androidx.compose.material.icons.sharp.Email
import androidx.compose.material.icons.sharp.MailOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.project.ui.theme.ProjectTheme

//https://developer.android.com/codelabs/basic-android-kotlin-compose-business-card?continue=https://developer.android.com/courses/pathways/android-basics-compose-unit-1-pathway-3%23codelab-https://developer.android.com/codelabs/basic-android-kotlin-compose-business-card#1
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProjectTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.primary
                ) {
                    BusinessCard()
                }
            }
        }
    }



    @Composable
    fun BusinessCard() {
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.DarkGray),
            ) {
                NamedHeader(fullName = "Chris Jones", title = "Web Developer")
                ContactInfo(
                    number = "+0(00)-000-000",
                    userId = "@socialmediaHandle",
                    email = "email@domain.com"
                )
            }
    }

    @Composable
    fun NamedHeader(
        fullName: String,
        title: String
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(top = 40.dp)

        ) {
            Icon(
                imageVector = Icons.Sharp.MailOutline,
                contentDescription = null,
                modifier = Modifier.size(150.dp)
            )
            Text(
                text = fullName,
                fontSize = 30.sp
            )
            Text(
                text = title,
                fontSize = 12.sp
            )
        }

    }

    @Composable
    fun ContactInfo(
        number: String,
        userId: String,
        email: String
    ) {
        Column {
            ContactInfoRow(Icons.Sharp.Call, number)
            ContactInfoRow(Icons.Sharp.AccountCircle, userId)
            ContactInfoRow(Icons.Sharp.Email, email)
        }
    }

    @Composable
    fun ContactInfoRow(
        imageVector: ImageVector,
        content: String
    ) {
        Row(
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Icon(
                imageVector = imageVector,
                contentDescription = null,
                modifier = Modifier.padding(horizontal = 12.dp)
            )
            Text(
                text = content
            )
        }
    }


    @Preview(showBackground = true)
    @Composable
    fun Preview() {
        ProjectTheme {
            BusinessCard()
        }
    }
}
