package com.serviciotecnico.model

// Representa los errores de validación del formulario de orden de servicio
data class ErrorFormularioOrden(
    val errorCliente: String? = null,
    val errorVehiculo: String? = null,
    val errorPatente: String? = null,
    val errorDescripcion: String? = null,
    val errorCostoTotal: String? = null // Añadido
)
