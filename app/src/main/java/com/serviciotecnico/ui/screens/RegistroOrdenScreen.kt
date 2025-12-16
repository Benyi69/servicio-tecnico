package com.serviciotecnico.ui.screens

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.serviciotecnico.viewmodel.ServiceViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroOrdenScreen(
    navController: NavController,
    vm: ServiceViewModel,
    clienteId: String?,
    clienteNombre: String?
) {
    var vehiculo by remember { mutableStateOf("") }
    var patente by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var imagenUri by remember { mutableStateOf<String?>(null) }
    var error by remember { mutableStateOf<String?>(null) }
    var showSuccessDialog by remember { mutableStateOf(false) }
    var newOrderId by remember { mutableStateOf("") }

    LaunchedEffect(clienteNombre) {
        clienteNombre?.let { vm.setCliente(it) }
    }

    val galeriaLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        imagenUri = uri?.toString()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Registrar Orden") },
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
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            OutlinedTextField(
                value = clienteNombre ?: "",
                onValueChange = { /* No se permite cambiar */ },
                label = { Text("Nombre del cliente") },
                modifier = Modifier.fillMaxWidth(),
                enabled = false
            )

            OutlinedTextField(
                value = vehiculo,
                onValueChange = { vehiculo = it },
                label = { Text("Vehículo (marca y modelo)") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = patente,
                onValueChange = { patente = it },
                label = { Text("Patente") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                label = { Text("Descripción del trabajo") },
                modifier = Modifier.fillMaxWidth().height(120.dp)
            )

            Button(onClick = { galeriaLauncher.launch("image/*") }) {
                Icon(Icons.Default.Photo, contentDescription = "Abrir galería")
                Spacer(Modifier.width(8.dp))
                Text("Seleccionar Imagen")
            }

            imagenUri?.let {
                Image(
                    painter = rememberAsyncImagePainter(it),
                    contentDescription = "Imagen seleccionada",
                    modifier = Modifier.fillMaxWidth().height(200.dp).padding(top = 8.dp)
                )
            }

            Spacer(Modifier.weight(1f))

            Button(
                onClick = {
                    if (clienteId != null && clienteNombre != null) {
                        vm.setCliente(clienteNombre)
                        vm.setVehiculo(vehiculo)
                        vm.setPatente(patente)
                        vm.setDescripcion(descripcion)
                        vm.setImagen(imagenUri)
                        vm.guardarTicket(clienteId) { generatedId ->
                            newOrderId = generatedId
                            showSuccessDialog = true
                        }
                    } else {
                        error = "Error: No se ha seleccionado un cliente."
                    }
                },
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                enabled = clienteId != null
            ) {
                Text("Guardar Orden")
            }
            error?.let {
                Text(it, color = MaterialTheme.colorScheme.error)
            }
        }
    }

    if (showSuccessDialog) {
        SuccessDialog(
            orderId = newOrderId,
            onDismiss = {
                showSuccessDialog = false
                navController.popBackStack() // Volver a la pantalla anterior
            }
        )
    }
}

@Composable
fun SuccessDialog(orderId: String, onDismiss: () -> Unit) {
    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("¡Orden Creada con Éxito!") },
        text = {
            Column {
                Text("La orden ha sido registrada. Proporcione el siguiente ID al cliente para su seguimiento:")
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = orderId,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("Aceptar")
            }
        },
        dismissButton = {
            TextButton(onClick = {
                clipboardManager.setText(AnnotatedString(orderId))
                Toast.makeText(context, "ID copiado al portapapeles", Toast.LENGTH_SHORT).show()
            }) {
                Text("Copiar ID")
            }
        }
    )
}
