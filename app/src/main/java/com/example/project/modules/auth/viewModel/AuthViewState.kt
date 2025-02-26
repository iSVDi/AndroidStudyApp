package com.example.project.modules.auth.viewModel

sealed class AuthViewState {
    object InitState: AuthViewState()
    object SuccessLogin: AuthViewState()
    object ErrorWhileLogin: AuthViewState()
}

