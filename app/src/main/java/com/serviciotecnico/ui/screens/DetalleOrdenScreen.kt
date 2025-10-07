package com.serviciotecnico.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.serviciotecnico.model.Arreglo
import com.serviciotecnico.viewmodel.ServiceViewModel
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleOrdenScreen(navController: NavController, vm: ServiceViewModel, id: Long) {

    LaunchedEffect(id) {
        vm.obtenerTicketPorId(id)
    }

    val ticket by vm.ticketDetalle.collectAsState()
    var mostrarDialogo by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Gestión de la Orden") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { mostrarDialogo = true }) {
                Icon(Icons.Default.Add, contentDescription = "Añadir Arreglo")
            }
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
            if (ticket == null) {
                CircularProgressIndicator()
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                    // --- Sección de Datos del Cliente ---
                    item {
                        Text("Detalles del Cliente y Vehículo", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(8.dp))
                        CampoDetalle("Cliente:", ticket!!.cliente)
                        CampoDetalle("Vehículo:", ticket!!.vehiculo)
                        CampoDetalle("Patente:", ticket!!.patente)
                        CampoDetalle("Fecha Registro:", ticket!!.fechaRegistro.toReadableDate()) 
                        CampoDetalle("Descripción Inicial:", ticket!!.descripcion)
                        Divider(Modifier.padding(vertical = 16.dp))
                    }

                    // --- Sección de Arreglos y Precios ---
                    item {
                        Text("Arreglos y Repuestos", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(8.dp))
                    }

                    items(ticket!!.arreglos) { arreglo ->
                        ItemArreglo(arreglo = arreglo, onEliminar = { vm.eliminarArreglo(arreglo) })
                    }

                    // --- Sección de Costo Total ---
                    item {
                        Spacer(Modifier.height(16.dp))
                        Divider()
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Costo Total:", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                            Text(
                                ticket!!.costoTotal.toCurrencyFormat(), 
                                style = MaterialTheme.typography.headlineSmall, 
                                fontWeight = FontWeight.Bold, 
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        }
    }

    if (mostrarDialogo) {
        DialogoAnadirArreglo(
            onDismiss = { mostrarDialogo = false },
            onConfirm = { descripcion, precio ->
                vm.insertarArreglo(ticket!!.id, descripcion, precio)
                mostrarDialogo = false
            }
        )
    }
}

// IMPLEMENTACIÓN CORRECTA DE LA FUNCIÓN QUE FALTABA
@Composable
fun CampoDetalle(label: String, value: String) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
        Text(label, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary, fontSize = 14.sp)
        Text(value, fontSize = 18.sp)
        Divider(color = Color.LightGray, thickness = 1.dp, modifier = Modifier.padding(top = 8.dp))
    }
}

@Composable
fun ItemArreglo(arreglo: Arreglo, onEliminar: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(arreglo.descripcion, modifier = Modifier.weight(1f))
        Text(arreglo.precio.toCurrencyFormat(), fontWeight = FontWeight.SemiBold)
        IconButton(onClick = onEliminar) {
            Icon(Icons.Default.Delete, contentDescription = "Eliminar Arreglo", tint = Color.Gray)
        }
    }
}

@Composable
fun DialogoAnadirArreglo(onDismiss: () -> Unit, onConfirm: (String, Double) -> Unit) {
    var descripcion by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Añadir Arreglo/Repuesto") },
        text = {
            Column {
                OutlinedTextField(
                    value = descripcion,
                    onValueChange = { descripcion = it },
                    label = { Text("Descripción") },
                    isError = error?.contains("descripción") == true
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = precio,
                    onValueChange = { precio = it },
                    label = { Text("Precio") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    isError = error?.contains("precio") == true
                )
                if (error != null) {
                    Text(error!!, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
                }
            }
        },
        confirmButton = {
            Button(onClick = {
                val precioDouble = precio.toDoubleOrNull()
                when {
                    descripcion.isBlank() -> error = "La descripción no puede estar vacía"
                    precioDouble == null -> error = "El precio debe ser un número válido"
                    else -> onConfirm(descripcion, precioDouble)
                }
            }) {
                Text("Añadir")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}

fun Double.toCurrencyFormat(): String {
    return NumberFormat.getCurrencyInstance(Locale("es", "CL")).format(this)
}

// FUNCIÓN QUE FALTABA
fun Long.toReadableDate(): String {
    val sdf = SimpleDateFormat("dd/MM/yyyy, HH:mm", Locale.getDefault())
    return sdf.format(Date(this))
}
