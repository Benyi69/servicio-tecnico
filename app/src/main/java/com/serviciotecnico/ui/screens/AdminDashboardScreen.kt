package com.serviciotecnico.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.serviciotecnico.model.OrderStatus
import com.serviciotecnico.model.ServiceTicket
import com.serviciotecnico.model.UserRole
import com.serviciotecnico.viewmodel.ServiceViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDashboardScreen(navController: NavController, vm: ServiceViewModel) {
    val uiState by vm.uiState.collectAsState()
    var ordenSeleccionada by remember { mutableStateOf<ServiceTicket?>(null) }
    val currentUser = uiState.currentUser
    val tickets = uiState.tickets

    val groupedTickets = tickets.groupBy { it.statusEnum }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Panel de Administrador") },
                actions = {
                    IconButton(onClick = { navController.navigate("perfil") }) {
                        Icon(Icons.Default.Person, contentDescription = "Perfil")
                    }
                }
            )
        },
        floatingActionButton = {
            if (currentUser?.role == UserRole.ADMIN || currentUser?.role == UserRole.TECNICO) {
                FloatingActionButton(onClick = { navController.navigate("seleccionar_cliente") }) {
                    Icon(Icons.Default.Add, contentDescription = "Nueva Orden")
                }
            }
        }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize()) {
            AnimatedVisibility(
                visible = !uiState.isOnline,
                enter = slideInVertically(initialOffsetY = { -it }),
                exit = slideOutVertically(targetOffsetY = { -it })
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Red)
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Warning, contentDescription = "Sin conexión", tint = Color.White)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Sin conexión a Internet", color = Color.White)
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                val statusOrder = listOf(
                    OrderStatus.NUEVA,
                    OrderStatus.EN_DIAGNOSTICO,
                    OrderStatus.ESPERANDO_APROBACION,
                    OrderStatus.APROBADA,
                    OrderStatus.EN_REPARACION,
                    OrderStatus.COMPLETADA,
                    OrderStatus.RECHAZADA
                )

                statusOrder.forEach { status ->
                    val ticketsForStatus = groupedTickets[status] ?: emptyList()
                    item {
                        Text(
                            text = "${status.displayName} (${ticketsForStatus.size})",
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                        )
                        Divider()
                    }
                    if (ticketsForStatus.isEmpty()) {
                        item {
                            Text("No hay órdenes en estado ${status.displayName}.", modifier = Modifier.padding(bottom = 16.dp))
                        }
                    } else {
                        items(ticketsForStatus) { ticket ->
                            TarjetaOrdenAdmin(
                                orden = ticket,
                                onClick = { navController.navigate("detalle_orden/${ticket.id}") },
                                onEliminar = { ordenSeleccionada = ticket },
                                showEliminar = currentUser?.role == UserRole.ADMIN
                            )
                        }
                    }
                }
            }

            ordenSeleccionada?.let { orden ->
                AlertDialog(
                    onDismissRequest = { ordenSeleccionada = null },
                    title = { Text("Eliminar orden") },
                    text = { Text("¿Deseas eliminar la orden de ${orden.cliente}?") },
                    confirmButton = {
                        TextButton(onClick = {
                            vm.eliminarTicket(orden.id) // Pasamos el ID
                            ordenSeleccionada = null
                        }) {
                            Text("Eliminar")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { ordenSeleccionada = null }) {
                            Text("Cancelar")
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun TarjetaOrdenAdmin(orden: ServiceTicket, onClick: () -> Unit, onEliminar: () -> Unit, showEliminar: Boolean) {
    val cardColor = if (orden.statusEnum == OrderStatus.COMPLETADA) {
        Color(0xFFE8F5E9)
    } else {
        MaterialTheme.colorScheme.surfaceVariant
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = cardColor)
    ) {
        Column(Modifier.padding(12.dp)) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                    if (orden.statusEnum == OrderStatus.COMPLETADA) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Completado",
                            tint = Color(0xFF4CAF50),
                            modifier = Modifier.size(20.dp).padding(end = 8.dp)
                        )
                    }
                    Text(orden.cliente, style = MaterialTheme.typography.titleMedium)
                }
                if (showEliminar) {
                    IconButton(onClick = onEliminar) {
                        Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                    }
                }
            }
            
            Spacer(Modifier.height(4.dp))
            Text(orden.vehiculo, style = MaterialTheme.typography.bodyMedium)
            Text(orden.descripcion, style = MaterialTheme.typography.bodySmall, maxLines = 2)
        }
    }
}
