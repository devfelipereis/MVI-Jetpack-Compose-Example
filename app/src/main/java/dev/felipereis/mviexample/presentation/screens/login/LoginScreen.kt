package dev.felipereis.mviexample.presentation.screens.login

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController

@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModel: LoginViewModel = viewModel()
) {
    LoginContent(
        state = viewModel.state.collectAsState(),
        onAction = viewModel::onAction
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoginContent(
    state: State<LoginState>,
    onAction: (LoginAction) -> Unit,
) {
    Scaffold {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                label = { Text(text = "Email") },
                value = state.value.email,
                readOnly = state.value.isLoading,
                onValueChange = { onAction(LoginAction.EmailChanged(it)) },
                isError = !state.value.isEmailValid,
                supportingText = {
                    if (!state.value.isEmailValid) {
                        Text(text = "Invalid email")
                    }
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                label = { Text(text = "Password") },
                value = state.value.password,
                readOnly = state.value.isLoading,
                onValueChange = { onAction(LoginAction.PasswordChanged(it)) },
                visualTransformation = PasswordVisualTransformation(),
                isError = !state.value.isPasswordValid,
                supportingText = {
                    if (!state.value.isPasswordValid) {
                        Text(text = "Invalid password")
                    }
                }
            )
            Spacer(modifier = Modifier.height(16.dp))

            if (state.value.isLoading) {
                CircularProgressIndicator()
            } else {
                TextButton(
                    enabled = state.value.isEmailValid && state.value.isPasswordValid,
                    onClick = { onAction(LoginAction.Login) }
                ) {
                    Text(text = "Login")
                }
            }
        }
    }
}