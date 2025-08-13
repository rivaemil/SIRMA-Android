// ui/login/LoginScreen.kt
package com.example.sirma_android.login

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun LoginScreen(
    vm: LoginViewModel,  // El ViewModel recibido como parámetro
    navController: NavController,
    onSuccess: () -> Unit
) {
    val state by vm.state.collectAsState()
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    LaunchedEffect(state) {
        if (state is LoginState.Success) {
            // Llama a onSuccess después de un login exitoso
            onSuccess()  // Esto dispara la navegación a "appointments"
        }
    }

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Iniciar sesión", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(16.dp))
        OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Correo") }, singleLine = true)
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(value = password, onValueChange = { password = it }, label = { Text("Contraseña") }, singleLine = true, visualTransformation = PasswordVisualTransformation())
        Spacer(Modifier.height(16.dp))
        Button(onClick = { vm.login(email, password) }, enabled = state !is LoginState.Loading) {
            Text(if (state is LoginState.Loading) "Entrando..." else "Entrar")
        }
        if (state is LoginState.Error) {
            Spacer(Modifier.height(8.dp))
            Text("Error: ${(state as LoginState.Error).msg}", color = MaterialTheme.colorScheme.error)
        }
    }
}
