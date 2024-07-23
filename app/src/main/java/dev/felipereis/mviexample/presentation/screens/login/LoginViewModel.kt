package dev.felipereis.mviexample.presentation.screens.login

import android.text.TextUtils
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    init {
        _state.map { it.email }.onEach {
            _state.value = _state.value.copy(isEmailValid = isValidEmail(it))
        }.launchIn(viewModelScope)

        _state.map { it.password }.onEach {
            _state.value = _state.value.copy(isPasswordValid = isValidPassword(it))
        }.launchIn(viewModelScope)
    }

    fun onAction(action: LoginAction) {
        when (action) {
            is LoginAction.Login -> loginIn(_state.value.email, _state.value.password)
            is LoginAction.EmailChanged -> onEmailChanged(action.value)
            is LoginAction.PasswordChanged -> onPasswordChanged(action.value)
        }
    }

    private fun onEmailChanged(email: String) {
        _state.value = _state.value.copy(email = email)
    }

    private fun onPasswordChanged(password: String) {
        _state.value = _state.value.copy(password = password)
    }

    private fun loginIn(email: String, password: String) {
        _state.value = _state.value.copy(isLoading = true)

        viewModelScope.launch {
            delay(2000)
            _state.value = _state.value.copy(isLoading = false)
        }
    }

    private fun isValidEmail(target: CharSequence?): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

    private fun isValidPassword(value: String): Boolean {
        return value.length >= 6
    }
}