package com.serviciotecnico.model

data class TicketFormState(
    val clienteNombre: String = "",
    val telefono: String = "",
    val tipoVehiculo: String = "Auto",
    val marca: String = "",
    val modelo: String = "",
    val patente: String = "",
    val descripcion: String = "",
    val fotoUri: String? = null
)
