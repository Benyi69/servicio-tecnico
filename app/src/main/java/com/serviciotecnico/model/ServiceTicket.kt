package com.serviciotecnico.model

data class ServiceTicket(
    val id: Long = 0,
    val clienteNombre: String,
    val telefono: String,
    val tipoVehiculo: String,
    val marca: String,
    val modelo: String,
    val patente: String,
    val descripcion: String,
    val fotoUri: String? = null,
    val estado: String = "Pendiente"
)
