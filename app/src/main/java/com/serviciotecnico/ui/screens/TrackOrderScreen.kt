package com.serviciotecnico.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.serviciotecnico.model.OrderStatus
import com.serviciotecnico.viewmodel.ServiceViewModel
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrackOrderScreen(navController: NavController, vm: ServiceViewModel) {
    var orderIdInput by remember { mutableStateOf("") }
    val uiState by vm.uiState.collectAsState()
    val trackedTicket = uiState.publicTrackedTicket

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Rastrear Orden") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
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
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = orderIdInput,
                onValueChange = { orderIdInput = it },
                label = { Text("ID de la Orden") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                modifier = Modifier.fillMaxWidth(),
                enabled = !uiState.isLoading
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { vm.obtenerOrdenPorIdPublico(orderIdInput) },
                modifier = Modifier.fillMaxWidth(),
                enabled = orderIdInput.isNotBlank() && !uiState.isLoading
            ) {
                Text("Buscar Orden")
            }

            if (uiState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.padding(top = 16.dp))
            }
            uiState.error?.let {
                Text(it, color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(top = 16.dp))
            }

            trackedTicket?.let { ticket ->
                Spacer(modifier = Modifier.height(24.dp))
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(16.dp)) {
                        Text("Orden #${ticket.id}", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(8.dp))
                        CampoDetalle("Cliente:", ticket.cliente)
                        CampoDetalle("Vehículo:", ticket.vehiculo)
                        CampoDetalle("Patente:", ticket.patente)
                        CampoDetalle("Estado:", ticket.statusEnum.displayName)
                        CampoDetalle("Descripción:", ticket.descripcion)
                        ticket.fechaRegistro?.let {
                            CampoDetalle("Fecha Registro:", it.toReadableDate())
                        }
                        Spacer(Modifier.height(16.dp))
                        Text("Costo Total: ${ticket.costoTotal.toCurrencyFormat()}", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
