package com.example.project.modules.auth.viewModel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project.common.dataBase.UserDataEntity
import com.example.project.common.dataBase.UserDataDao
import kotlinx.coroutines.launch


class AuthViewModel(private val userDataDao: UserDataDao) : ViewModel() {
    val state = mutableStateOf<AuthViewState>(AuthViewState.InitState)

    fun onCreateHandle() {
        insertUserDatasIfNeed()
    }

    fun handleAlertOkAction() {
        state.value = AuthViewState.InitState
    }

    fun checkUserBy(userName: String, password: String) {
        viewModelScope.launch {
            val users = userDataDao.getAll()
            if (users.find {
                    it.userName == userName && it.password == password
                } != null) {
                state.value = AuthViewState.SuccessLogin
            } else {
                state.value = AuthViewState.ErrorWhileLogin
            }
        }
    }

    private fun insertUserDatasIfNeed() {
        viewModelScope.launch {
            if (userDataDao.getAll().isEmpty()) {
                try {
                    userDataDao.insertAllUserDatas(
                        UserDataEntity(userName = "AuroraNova", password = "G3n3r@t1v3P@$$"),
                        UserDataEntity(userName = "CyberWanderer", password = "D@t@Str3@m"),
                        UserDataEntity(userName = "PixelVoyager", password = "V1rtu@lR3@l1ty"),
                        UserDataEntity(userName = "CodeAlchemist", password = "lg0r1thm1c"),
                        UserDataEntity(userName = "QuantumDreamer", password = "ncrypt10nK3"),
                        UserDataEntity(userName = "admin", password = "admin"),
                    )
                } catch (e: Exception) {
                    Log.e("Insert Error exp", e.message.orEmpty())
                }

            }
        }
    }

}