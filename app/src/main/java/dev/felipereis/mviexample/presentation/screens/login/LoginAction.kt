package dev.felipereis.mviexample.presentation.screens.login

sealed interface LoginAction {
    data object Login : LoginAction
    data class EmailChanged(val value: String): LoginAction
    data class PasswordChanged(val value: String): LoginAction
}