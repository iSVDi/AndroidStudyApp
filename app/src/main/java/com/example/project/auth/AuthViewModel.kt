package com.example.project.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.project.App
import com.example.project.dataBase.Entities.UserEntity
import com.example.project.dataBase.RecipeDataBase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class AuthViewModel(recipeDataBase: RecipeDataBase) : ViewModel() {
    val usersList = recipeDataBase.userDao.getUsers()

    companion object {
        val factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val app = checkNotNull(extras[APPLICATION_KEY]) as App
                val db = app.recipeDataBase

                return AuthViewModel(recipeDataBase = db) as T
            }
        }
    }

    init {
        viewModelScope.launch {
            insertDefaultUsersDataIfNeed(recipeDataBase)
        }
    }

    private suspend fun insertDefaultUsersDataIfNeed(db: RecipeDataBase) {
        if (db.userDao.getUsers().first().isEmpty()) {
            val defaultUsers = listOf(
                UserEntity(userName = "AuroraNova", password = "G3n3r@t1v3P@$$"),
                UserEntity(userName = "CyberWanderer", password = "D@t@Str3@m"),
                UserEntity(userName = "PixelVoyager", password = "V1rtu@lR3@l1ty"),
                UserEntity(userName = "CodeAlchemist", password = "@lg0r1thm1c"),
                UserEntity(userName = "QuantumDreamer", password = "3ncrypt10nK3")
            )
            defaultUsers.forEach {
                db.userDao.insertUserData(it)
            }
            Log.i("UsersAdded", "Users Added")
        } else {
            Log.i("Users exists", "Users Exists")
        }

    }
}