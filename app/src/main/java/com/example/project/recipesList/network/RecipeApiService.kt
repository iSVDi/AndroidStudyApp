package com.example.project.recipesList.network

import com.example.project.recipesList.models.RecipeAnswer
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://dummyjson.com"

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

interface RecipesApiService {
    @GET("/recipes?limit=0")
   suspend fun getRecipes(): RecipeAnswer
}

object RecipesApi {
    val retrofitService: RecipesApiService by lazy {
        retrofit.create(RecipesApiService::class.java)
    }
}
