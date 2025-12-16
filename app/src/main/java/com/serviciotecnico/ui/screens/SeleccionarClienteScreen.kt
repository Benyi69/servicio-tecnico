package com.serviciotecnico.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.serviciotecnico.model.Cliente
import com.serviciotecnico.viewmodel.ServiceViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeleccionarClienteScreen(navController: NavController, vm: ServiceViewModel) {
    val uiState by vm.uiState.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        vm.obtenerClientes()
    }

    val filteredClients = uiState.clientList.filter {
        it.name.contains(searchQuery, ignoreCase = true) ||
        (it.email?.contains(searchQuery, ignoreCase = true) == true) ||
        (it.phone?.contains(searchQuery, ignoreCase = true) == true)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Seleccionar Cliente") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                },
                actions = {
                    IconButton(onClick = { navController.navigate("registrar_cliente_invitado") }) {
                        Icon(Icons.Default.PersonAdd, contentDescription = "Registrar Cliente Invitado")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Buscar por nombre, email o teléfono") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(filteredClients) { client ->
                    ClientCard(client = client) {
                        navController.navigate("registro_orden?clienteId=${client.id}&clienteNombre=${client.name}")
                    }
                }
            }
        }
    }
}

@Composable
fun ClientCard(client: Cliente, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(client.name, style = MaterialTheme.typography.titleMedium)
            client.email?.let { Text(it, style = MaterialTheme.typography.bodySmall) }
            client.phone?.let { Text(it, style = MaterialTheme.typography.bodySmall) }
            if (!client.isRegistered) {
                Text("Cliente Invitado", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}
