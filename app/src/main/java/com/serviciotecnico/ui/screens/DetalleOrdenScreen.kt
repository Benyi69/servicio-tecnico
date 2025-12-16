package com.serviciotecnico.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.serviciotecnico.model.Arreglo
import com.serviciotecnico.model.OrderStatus
import com.serviciotecnico.model.UserRole
import com.serviciotecnico.viewmodel.ServiceViewModel
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleOrdenScreen(navController: NavController, vm: ServiceViewModel, id: String) {

    LaunchedEffect(id) {
        vm.obtenerTicketPorId(id)
    }

    val ticket by vm.ticketDetalle.collectAsState()
    val uiState by vm.uiState.collectAsState()
    val currentUser = uiState.currentUser
    val esAdminOTecnico = currentUser?.role == UserRole.ADMIN || currentUser?.role == UserRole.TECNICO

    var mostrarDialogoArreglo by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle de la Orden") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        },
        floatingActionButton = {
            if (esAdminOTecnico) {
                FloatingActionButton(onClick = { mostrarDialogoArreglo = true }) {
                    Icon(Icons.Default.Add, contentDescription = "Añadir Arreglo")
                }
            }
        }
    ) { padding ->
        if (ticket == null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
            ) {
                // --- Sección de ID de Orden (para Admin/Técnico) ---
                if (esAdminOTecnico) {
                    item {
                        OrderIdSection(orderId = ticket!!.id)
                        Divider(Modifier.padding(vertical = 16.dp))
                    }
                }

                // --- Sección de Estado ---
                item {
                    EstadoSection(
                        ticketStatus = ticket!!.statusEnum,
                        esAdmin = esAdminOTecnico,
                        onStatusChange = { nuevoEstado ->
                            vm.actualizarEstadoOrden(ticket!!.id, nuevoEstado)
                        }
                    )
                    Divider(Modifier.padding(vertical = 16.dp))
                }

                // --- Sección de Aprobación del Cliente ---
                if (ticket!!.statusEnum == OrderStatus.ESPERANDO_APROBACION && !esAdminOTecnico) {
                    item {
                        AprobacionSection(
                            onAprobar = { vm.actualizarEstadoOrden(ticket!!.id, OrderStatus.APROBADA) },
                            onRechazar = { vm.actualizarEstadoOrden(ticket!!.id, OrderStatus.RECHAZADA) }
                        )
                        Divider(Modifier.padding(vertical = 16.dp))
                    }
                }

                // --- Detalles del Ticket ---
                item {
                    Text("Detalles", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                    CampoDetalle("Cliente:", ticket!!.cliente)
                    CampoDetalle("Vehículo:", ticket!!.vehiculo)
                    CampoDetalle("Patente:", ticket!!.patente)
                    ticket!!.fechaRegistro?.let {
                        CampoDetalle("Fecha Registro:", it.toReadableDate())
                    }
                    CampoDetalle("Descripción:", ticket!!.descripcion)
                    Divider(Modifier.padding(vertical = 16.dp))
                }

                // --- Arreglos y Presupuesto ---
                item {
                    Text("Presupuesto", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                }
                items(ticket!!.arreglos) { arreglo ->
                    ItemArreglo(
                        arreglo = arreglo,
                        onEliminar = { /* No-op por ahora */ },
                        showEliminar = false
                    )
                }
                item {
                    Spacer(Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
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

    if (mostrarDialogoArreglo) {
        DialogoAnadirArreglo(
            onDismiss = { mostrarDialogoArreglo = false },
            onConfirm = { descripcion, precio ->
                vm.insertarArreglo(ticket!!.id, descripcion, precio)
                mostrarDialogoArreglo = false
            }
        )
    }
}

@Composable
fun OrderIdSection(orderId: String) {
    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current

    Column {
        Text("ID de Seguimiento", style = MaterialTheme.typography.titleMedium)
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = orderId,
                style = MaterialTheme.typography.bodyLarge,
                fontFamily = FontFamily.Monospace // Corregido
            )
            IconButton(onClick = {
                clipboardManager.setText(AnnotatedString(orderId))
                Toast.makeText(context, "ID copiado al portapapeles", Toast.LENGTH_SHORT).show()
            }) {
                Icon(Icons.Default.ContentCopy, contentDescription = "Copiar ID")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EstadoSection(ticketStatus: OrderStatus, esAdmin: Boolean, onStatusChange: (OrderStatus) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        Text("Estado Actual", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(8.dp))

        if (esAdmin) {
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = ticketStatus.displayName,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier.fillMaxWidth().menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    OrderStatus.values().forEach { status ->
                        DropdownMenuItem(
                            text = { Text(status.displayName) },
                            onClick = {
                                onStatusChange(status)
                                expanded = false
                            }
                        )
                    }
                }
            }
        } else {
            Text(ticketStatus.displayName, style = MaterialTheme.typography.headlineSmall, color = MaterialTheme.colorScheme.primary)
        }
    }
}

@Composable
fun AprobacionSection(onAprobar: () -> Unit, onRechazar: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("El taller ha enviado un presupuesto.", style = MaterialTheme.typography.titleMedium)
        Text("Por favor, revísalo y elige una opción:")
        Spacer(Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = onAprobar, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))) {
                Text("Aprobar Presupuesto")
            }
            Button(onClick = onRechazar, colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)) {
                Text("Rechazar")
            }
        }
    }
}

@Composable
fun CampoDetalle(label: String, value: String) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
        Text(label, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary, fontSize = 14.sp)
        Text(value, fontSize = 18.sp)
        Divider(color = Color.LightGray, thickness = 1.dp, modifier = Modifier.padding(top = 8.dp))
    }
}

@Composable
fun ItemArreglo(arreglo: Arreglo, onEliminar: () -> Unit, showEliminar: Boolean) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(arreglo.descripcion, modifier = Modifier.weight(1f))
        Text(arreglo.precio.toCurrencyFormat(), fontWeight = FontWeight.SemiBold)
        if (showEliminar) {
            IconButton(onClick = onEliminar) {
                Icon(Icons.Default.Delete, contentDescription = "Eliminar Arreglo", tint = Color.Gray)
            }
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

fun Long.toReadableDate(): String {
    val sdf = SimpleDateFormat("dd/MM/yyyy, HH:mm", Locale.getDefault())
    return sdf.format(Date(this))
}
