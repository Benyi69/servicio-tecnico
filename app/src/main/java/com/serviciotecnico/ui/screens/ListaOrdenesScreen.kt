package com.serviciotecnico.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.serviciotecnico.model.ServiceTicket
import com.serviciotecnico.viewmodel.ServiceViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaOrdenesScreen(navController: NavController, vm: ServiceViewModel) {
    val uiState by vm.uiState.collectAsState()
    var ordenSeleccionada by remember { mutableStateOf<ServiceTicket?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Órdenes de Servicio") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("registro") }) {
                Icon(Icons.Default.Add, contentDescription = "Agregar orden")
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier.fillMaxSize().padding(padding),
            contentAlignment = Alignment.Center
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator()
            } else if (uiState.error != null) {
                Text(text = "Error: ${uiState.error}")
            } else if (uiState.tickets.isEmpty()) {
                Text(text = "No hay órdenes registradas.")
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(uiState.tickets) { orden ->
                        TarjetaOrden(
                            orden = orden,
                            onClick = { navController.navigate("detalle_orden/${orden.id}") },
                            onEliminar = { ordenSeleccionada = orden }
                        )
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
                            vm.eliminarTicket(orden)
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
fun TarjetaOrden(orden: ServiceTicket, onClick: () -> Unit, onEliminar: () -> Unit) {
    val cardColor = if (orden.completado) {
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
                    if (orden.completado) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Completado",
                            tint = Color(0xFF4CAF50),
                            modifier = Modifier.size(20.dp).padding(end = 8.dp)
                        )
                    }
                    Text(orden.cliente, style = MaterialTheme.typography.titleMedium)
                }
                IconButton(onClick = onEliminar) {
                    Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                }
            }
            
            Spacer(Modifier.height(4.dp))
            Text(orden.vehiculo, style = MaterialTheme.typography.bodyMedium)
            Text(orden.descripcion, style = MaterialTheme.typography.bodySmall, maxLines = 2)

            orden.imagenUri?.let { it ->
                Spacer(Modifier.height(8.dp))
                Image(
                    painter = rememberAsyncImagePainter(it),
                    contentDescription = "Imagen de la orden",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .padding(top = 4.dp)
                )
            }
        }
    }
}
