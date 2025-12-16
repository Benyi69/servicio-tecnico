package com.serviciotecnico.model

// Guarda temporalmente los valores del formulario de orden de servicio
data class EstadoFormularioOrden(
    val cliente: String = "",
    val vehiculo: String = "",
    val patente: String = "",
    val descripcion: String = "",
    val costoTotal: String = "",
    val imagenUri: String? = null
)
