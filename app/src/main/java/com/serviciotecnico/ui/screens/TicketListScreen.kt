package com.serviciotecnico.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.serviciotecnico.viewmodel.ServiceViewModel


@OptIn(ExperimentalMaterial3Api::class) // <-- AÑADE ESTA LÍNEA
@Composable
fun TicketListScreen(navController: NavController, vm: ServiceViewModel) {
    val tickets by vm.tickets.collectAsState()

    Scaffold(topBar = {
        TopAppBar(title = { Text("Solicitudes") }, actions = { // El error era por este componente
            IconButton(onClick = { navController.navigate("registro") }) {
                Icon(Icons.Default.Add, contentDescription = "Nuevo")
            }
        })
    }) { padding ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (tickets.isEmpty()) {
                Text("No hay solicitudes aún. Presiona + para crear una.")
            } else {
                tickets.forEach { t ->
                    Card(modifier = Modifier
                        .fillMaxWidth()
                        .clickable { /* detalle */ }
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text("${t.clienteNombre} — ${t.tipoVehiculo}", style = MaterialTheme.typography.titleMedium)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("Patente: ${t.patente}")
                            Text("Estado: ${t.estado}")
                        }
                    }
                }
            }
        }
    }
}
