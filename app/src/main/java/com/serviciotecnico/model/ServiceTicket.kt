package com.serviciotecnico.model

/**
 * Modelo principal que representa una orden de servicio.
 */
data class ServiceTicket(
    val id: Long = 0,
    val cliente: String,
    val vehiculo: String,
    val patente: String,
    val descripcion: String,
    val completado: Boolean = false,
    val imagenUri: String? = null,
    val fechaRegistro: Long = System.currentTimeMillis(),
    val arreglos: List<Arreglo> = emptyList() // Lista de arreglos añadida
) {
    // El costo total ahora es una propiedad calculada que suma los precios de los arreglos.
    val costoTotal: Double
        get() = arreglos.sumOf { it.precio }
}
