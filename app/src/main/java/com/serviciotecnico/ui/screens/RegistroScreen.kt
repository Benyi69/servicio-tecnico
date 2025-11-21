package com.serviciotecnico.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.serviciotecnico.viewmodel.ServiceViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroScreen(navController: NavController, vm: ServiceViewModel) {
    val formulario by vm.formulario.collectAsState()
    val errores by vm.errores.collectAsState()

    val galeriaLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        vm.setImagen(uri?.toString())
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
                value = formulario.cliente,
                onValueChange = { vm.setCliente(it) },
                label = { Text("Nombre del cliente") },
                isError = errores.errorCliente != null,
                supportingText = { errores.errorCliente?.let { Text(it) } },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = formulario.vehiculo,
                onValueChange = { vm.setVehiculo(it) },
                label = { Text("Vehículo (marca y modelo)") },
                isError = errores.errorVehiculo != null,
                supportingText = { errores.errorVehiculo?.let { Text(it) } },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = formulario.patente,
                onValueChange = { vm.setPatente(it) },
                label = { Text("Patente") },
                isError = errores.errorPatente != null,
                supportingText = { errores.errorPatente?.let { Text(it) } },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = formulario.descripcion,
                onValueChange = { vm.setDescripcion(it) },
                label = { Text("Descripción del trabajo") },
                isError = errores.errorDescripcion != null,
                supportingText = { errores.errorDescripcion?.let { Text(it) } },
                modifier = Modifier.fillMaxWidth().height(120.dp)
            )

            Button(onClick = { galeriaLauncher.launch("image/*") }) {
                Icon(Icons.Default.Photo, contentDescription = "Abrir galería")
                Spacer(Modifier.width(8.dp))
                Text("Seleccionar Imagen")
            }

            formulario.imagenUri?.let {
                Image(
                    painter = rememberAsyncImagePainter(it),
                    contentDescription = "Imagen seleccionada",
                    modifier = Modifier.fillMaxWidth().height(200.dp).padding(top = 8.dp)
                )
            }

            Spacer(Modifier.weight(1f))

            Button(
                onClick = {
                    vm.guardarTicket { nuevoId ->
                        navController.navigate("detalle_orden/$nuevoId") {
                            popUpTo("lista")
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
            ) {
                Text("Guardar y Añadir Arreglos")
            }
        }
    }
}
