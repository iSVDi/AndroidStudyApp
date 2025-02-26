package com.example.project.common

import android.content.Context

enum class SharedPreferencesKeys(value: String) {
    isFirstLaunched("isFirstLaunched")

}
class SharedPreferencesHelper(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("MyAppPreferences",
        Context.MODE_PRIVATE
    )

    fun updateIsFirstLaunched() {
        sharedPreferences.edit().putBoolean(SharedPreferencesKeys.isFirstLaunched.name, true).apply()
    }

    fun getIsFirstLaunched(): Boolean {
        return sharedPreferences.getBoolean(SharedPreferencesKeys.isFirstLaunched.name, false)
    }
}