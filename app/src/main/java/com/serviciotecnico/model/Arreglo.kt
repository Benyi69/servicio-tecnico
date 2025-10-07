package com.serviciotecnico.model

/**
 * Representa un solo ítem de servicio o repuesto dentro de una orden.
 * @param id El identificador único del arreglo.
 * @param descripcion La descripción del trabajo o repuesto (ej: "Cambio de aceite").
 * @param precio El costo de este ítem en específico.
 * @param ticketId El ID de la orden de servicio a la que pertenece.
 */
data class Arreglo(
    val id: Long = 0,
    val descripcion: String,
    val precio: Double,
    val ticketId: Long
)
