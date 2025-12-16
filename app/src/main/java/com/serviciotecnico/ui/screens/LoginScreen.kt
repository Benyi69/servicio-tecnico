package com.serviciotecnico.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.serviciotecnico.model.UserRole
import com.serviciotecnico.viewmodel.ServiceViewModel

@Composable
fun LoginScreen(navController: NavController, vm: ServiceViewModel) {

    var email by remember { mutableStateOf("") }
    var clave by remember { mutableStateOf("") }
    val uiState by vm.uiState.collectAsState()
    val loginExitoso by vm.loginExitoso.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Iniciar Sesión", style = MaterialTheme.typography.headlineMedium)
            Spacer(Modifier.height(24.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Correo Electrónico") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth(),
                enabled = !uiState.isLoading
            )
            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = clave,
                onValueChange = { clave = it },
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth(),
                enabled = !uiState.isLoading
            )
            Spacer(Modifier.height(8.dp))
            TextButton(onClick = { navController.navigate("recuperar_pass") }) {
                Text("¿Olvidaste tu contraseña?")
            }
            Spacer(Modifier.height(8.dp))

            Button(
                onClick = { vm.login(email, clave) },
                modifier = Modifier.fillMaxWidth(),
                enabled = !uiState.isLoading
            ) {
                Text("Ingresar")
            }
            Spacer(Modifier.height(8.dp))
            TextButton(onClick = { navController.navigate("registro_usuario") }) {
                Text("¿No tienes una cuenta? Regístrate")
            }
            Spacer(Modifier.height(8.dp))
            TextButton(onClick = { navController.navigate("track_order") }) {
                Text("Rastrear mi Orden")
            }

            uiState.error?.let {
                Spacer(Modifier.height(16.dp))
                Text(it, color = Color.Red)
            }
        }

        if (uiState.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }

    if (loginExitoso) {
        LaunchedEffect(uiState.currentUser) {
            val user = uiState.currentUser
            if (user != null) {
                val destination = when (user.role) {
                    UserRole.ADMIN, UserRole.TECNICO -> "lista"
                    UserRole.CLIENTE -> "cliente_dashboard"
                    else -> "login"
                }
                navController.navigate(destination) {
                    popUpTo("login") { inclusive = true }
                }
            }
        }
    }
}
