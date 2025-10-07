package com.serviciotecnico.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.serviciotecnico.viewmodel.ServiceViewModel

@Composable
fun RegistroScreen(navController: NavController, vm: ServiceViewModel) {
    val form by vm.form.collectAsState()
    val errors by vm.errors.collectAsState()
    val context = LocalContext.current

    val imageLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        vm.setFotoUri(uri?.toString())
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Registrar solicitud", style = MaterialTheme.typography.titleLarge)

        OutlinedTextField(
            value = form.clienteNombre,
            onValueChange = { vm.setClienteNombre(it) },
            label = { Text("Nombre cliente") },
            isError = errors.clienteNombreError != null,
            supportingText = { errors.clienteNombreError?.let { Text(it) } },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = form.telefono,
            onValueChange = { vm.setTelefono(it) },
            label = { Text("Teléfono") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            isError = errors.telefonoError != null,
            supportingText = { errors.telefonoError?.let { Text(it) } },
            modifier = Modifier.fillMaxWidth()
        )

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { vm.setTipoVehiculo("Auto") }, modifier = Modifier.weight(1f)) {
                Text("Auto")
            }
            Button(onClick = { vm.setTipoVehiculo("Moto") }, modifier = Modifier.weight(1f)) {
                Text("Moto")
            }
        }

        OutlinedTextField(
            value = form.marca,
            onValueChange = { vm.setMarca(it) },
            label = { Text("Marca") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = form.modelo,
            onValueChange = { vm.setModelo(it) },
            label = { Text("Modelo") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = form.patente,
            onValueChange = { vm.setPatente(it) },
            label = { Text("Patente") },
            isError = errors.patenteError != null,
            supportingText = { errors.patenteError?.let { Text(it) } },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = form.descripcion,
            onValueChange = { vm.setDescripcion(it) },
            label = { Text("Descripción del problema") },
            isError = errors.descripcionError != null,
            supportingText = { errors.descripcionError?.let { Text(it) } },
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
        )

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { imageLauncher.launch("image/*") }) {
                Text("Agregar foto")
            }
            Button(onClick = {
                val tel = form.telefono
                if (tel.isNotBlank()) {
                    val intent = Intent(Intent.ACTION_DIAL)
                    intent.data = Uri.parse("tel:$tel")
                    context.startActivity(intent)
                }
            }) {
                Text("Llamar cliente")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(modifier = Modifier.fillMaxWidth(), onClick = {
            vm.submitTicket { id ->
                if (id != null) {
                    navController.navigate("lista") {
                        popUpTo("lista") { inclusive = false }
                    }
                }
            }
        }) {
            Text("Enviar solicitud")
        }
    }
}
