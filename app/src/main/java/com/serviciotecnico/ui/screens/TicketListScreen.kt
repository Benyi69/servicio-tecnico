package com.serviciotecnico.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.serviciotecnico.model.ServiceTicket
import com.serviciotecnico.viewmodel.ServiceViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicketListScreen(navController: NavController, vm: ServiceViewModel) {
    val uiState by vm.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Órdenes de Servicio") },
                actions = {
                    IconButton(onClick = { navController.navigate("registro") }) {
                        Icon(Icons.Default.Add, contentDescription = "Nueva Orden")
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier.fillMaxSize().padding(padding),
            contentAlignment = Alignment.Center
        ) {
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator()
                }
                uiState.error != null -> {
                    Text("Error: ${uiState.error}")
                }
                uiState.tickets.isEmpty() -> {
                    Text("No hay órdenes registradas. Presiona + para crear una.")
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize().padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(uiState.tickets) { ticket ->
                            TicketCard(ticket = ticket) {
                                navController.navigate("detalle_orden/${ticket.id}")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TicketCard(ticket: ServiceTicket, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Text(ticket.cliente, style = MaterialTheme.typography.titleMedium)
                Text(ticket.vehiculo, style = MaterialTheme.typography.bodySmall)
            }
            Text(ticket.costoTotal.toCurrencyFormat(), style = MaterialTheme.typography.bodyLarge)
        }
    }
}
