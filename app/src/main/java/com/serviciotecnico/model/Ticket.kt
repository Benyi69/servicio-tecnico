package com.serviciotecnico.model

data class Ticket(
    val id: Long = 0,
    val clienteNombre: String,
    val tipoVehiculo: String,
    val patente: String,
    val estado: String
)
