package com.serviciotecnico.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.serviciotecnico.model.OrderStatus
import com.serviciotecnico.model.ServiceOffering
import com.serviciotecnico.model.ServiceTicket
import com.serviciotecnico.viewmodel.ServiceViewModel
import java.text.NumberFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClienteDashboardScreen(navController: NavController, vm: ServiceViewModel) {
    val uiState by vm.uiState.collectAsState()
    val currentUser = uiState.currentUser
    val tickets = uiState.tickets
    val activeTickets = tickets.filter { it.statusEnum != OrderStatus.COMPLETADA && it.statusEnum != OrderStatus.RECHAZADA }
    val completedTickets = tickets.filter { it.statusEnum == OrderStatus.COMPLETADA || it.statusEnum == OrderStatus.RECHAZADA }
    val serviceOfferings = uiState.serviceOfferings

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis Órdenes") },
                actions = {
                    IconButton(onClick = { navController.navigate("perfil") }) {
                        Icon(Icons.Default.Person, contentDescription = "Perfil")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    text = "Hola, ${currentUser?.name ?: "Cliente"}!",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(24.dp))
            }

            // Sección de Catálogo de Servicios
            item {
                Text("Nuestros Servicios", style = MaterialTheme.typography.titleLarge)
                Divider(modifier = Modifier.padding(vertical = 8.dp))
            }
            if (serviceOfferings.isEmpty()) {
                item {
                    Text("No hay servicios disponibles en este momento.")
                }
            } else {
                items(serviceOfferings) { offering ->
                    ServiceOfferingCard(offering = offering)
                }
            }
            item {
                Spacer(modifier = Modifier.height(24.dp))
            }


            item {
                Text("Órdenes Activas (${activeTickets.size})", style = MaterialTheme.typography.titleLarge)
                Divider(modifier = Modifier.padding(vertical = 8.dp))
            }
            if (activeTickets.isEmpty()) {
                item {
                    Text("No tienes órdenes activas.", modifier = Modifier.padding(bottom = 16.dp))
                }
            } else {
                items(activeTickets) { ticket ->
                    TimelineCard(ticket = ticket, onClick = { navController.navigate("detalle_orden/${ticket.id}") })
                }
            }

            item {
                Text("Historial (${completedTickets.size})", style = MaterialTheme.typography.titleLarge)
                Divider(modifier = Modifier.padding(vertical = 8.dp))
            }
            if (completedTickets.isEmpty()) {
                item {
                    Text("No tienes órdenes en tu historial.")
                }
            } else {
                items(completedTickets) { ticket ->
                    TimelineCard(ticket = ticket, onClick = { navController.navigate("detalle_orden/${ticket.id}") })
                }
            }
        }
    }
}

@Composable
fun TimelineCard(ticket: ServiceTicket, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(ticket.vehiculo, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Text("Patente: ${ticket.patente}", style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(16.dp))
            
            // Línea de tiempo del estado
            Row(Modifier.fillMaxWidth()) {
                OrderStatus.values().forEach { status ->
                    val isActive = ticket.statusEnum.ordinal >= status.ordinal
                    val color = if (isActive) MaterialTheme.colorScheme.primary else Color.LightGray
                    
                    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(1f)) {
                        Box(
                            modifier = Modifier
                                .size(12.dp)
                                .background(color, CircleShape)
                        )
                        Text(
                            text = status.name.lowercase().replaceFirstChar { it.uppercase() },
                            style = MaterialTheme.typography.labelSmall,
                            color = if (isActive) MaterialTheme.colorScheme.onSurface else Color.Gray
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ServiceOfferingCard(offering: ServiceOffering) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(offering.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(4.dp))
            Text(offering.description, style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(8.dp))
            Text(
                "Precio Aprox: ${NumberFormat.getCurrencyInstance(Locale("es", "CL")).format(offering.priceApprox)}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary
            )
            offering.category.takeIf { it.isNotBlank() }?.let {
                Text("Categoría: $it", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
            }
        }
    }
}
