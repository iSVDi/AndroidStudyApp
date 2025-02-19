package com.example.project


import android.graphics.Paint.Align
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.project.ui.theme.ProjectTheme

// https://developer.android.com/codelabs/basic-android-kotlin-compose-composables-practice-problems?continue=https://developer.android.com/courses/pathways/android-basics-compose-unit-1-pathway-3%23codelab-https://developer.android.com/codelabs/basic-android-kotlin-compose-composables-practice-problems#3
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProjectTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.primary
                ) {

                }
            }
        }
    }

    @Composable
    fun Quadrant() {
        Row(
            horizontalArrangement = Arrangement.Absolute.SpaceBetween
        ) {
            TextBlock(
                title = "Text composable",
                message = "Displays text and follows the recommended Material Design guidelines.",
                color = Color(0xFFEADDFF)
            )
            TextBlock(
                title = "Image composable",
                message = "Creates a composable that lays out and draws a given Painter class object.",
                color = Color(0xFFD0BCFF)
            )
        }
    }

    @Composable
    fun TextBlock(
        title: String,
        message: String,
        color: Color
    ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                        modifier = Modifier.background(color).padding(16.dp)

            ) {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = message
                )

        }
    }


@Preview(showBackground = true)
@Composable
    fun Preview() {
        ProjectTheme {
            Quadrant()
        }
    }
}
