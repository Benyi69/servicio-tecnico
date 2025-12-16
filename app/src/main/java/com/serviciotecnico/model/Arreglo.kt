package com.serviciotecnico.model

import com.google.firebase.firestore.PropertyName

/**
 * Representa un solo ítem de servicio o repuesto dentro de una orden.
 */
data class Arreglo @JvmOverloads constructor(
    @get:PropertyName("id") @set:PropertyName("id") var id: Long = 0,
    @get:PropertyName("descripcion") @set:PropertyName("descripcion") var descripcion: String = "",
    @get:PropertyName("precio") @set:PropertyName("precio") var precio: Double = 0.0,
    @get:PropertyName("ticketId") @set:PropertyName("ticketId") var ticketId: String = ""
)
