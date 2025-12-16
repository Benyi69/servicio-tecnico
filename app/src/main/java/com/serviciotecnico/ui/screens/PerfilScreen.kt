package com.serviciotecnico.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.serviciotecnico.viewmodel.ServiceViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilScreen(navController: NavController, vm: ServiceViewModel) {
    val uiState by vm.uiState.collectAsState()
    val currentUser = uiState.currentUser
    var nombre by remember { mutableStateOf(currentUser?.name ?: "") }

    // Observar si el usuario se vuelve nulo (cierre de sesión) para navegar al login
    LaunchedEffect(currentUser) {
        if (currentUser == null) {
            navController.navigate("login") {
                // Limpiar todo el historial de navegación
                popUpTo(navController.graph.startDestinationId) {
                    inclusive = true
                }
                launchSingleTop = true
            }
        } else {
            nombre = currentUser.name
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mi Perfil") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                },
                actions = {
                    IconButton(onClick = { vm.signOut() }) {
                        Icon(Icons.Default.ExitToApp, contentDescription = "Cerrar Sesión")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (currentUser != null) {
                Text("Email: ${currentUser.email}", style = MaterialTheme.typography.bodyLarge)
                Text("Rol: ${currentUser.role}", style = MaterialTheme.typography.bodyLarge)
                
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre para mostrar") },
                    modifier = Modifier.fillMaxWidth()
                )

                Button(
                    onClick = { vm.actualizarPerfil(nombre) },
                    modifier = Modifier.align(Alignment.End),
                    enabled = nombre.isNotBlank() && nombre != currentUser.name
                ) {
                    Text("Guardar Cambios")
                }

            } else {
                // Muestra un indicador de carga mientras se cierra la sesión
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}
