package com.example.project

import android.app.Application
import com.example.project.dataBase.RecipeDataBase

class App: Application() {
    val recipeDataBase by lazy { RecipeDataBase.createDataBase(this)}
}