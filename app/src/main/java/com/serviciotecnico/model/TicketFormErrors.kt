package com.serviciotecnico.model

data class TicketFormErrors(
    val clienteNombreError: String? = null,
    val telefonoError: String? = null,
    val patenteError: String? = null,
    val descripcionError: String? = null
)
