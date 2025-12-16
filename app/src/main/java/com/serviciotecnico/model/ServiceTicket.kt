package com.serviciotecnico.model

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.PropertyName

data class ServiceTicket @JvmOverloads constructor(
    @DocumentId
    @get:PropertyName("id") @set:PropertyName("id") var id: String = "",

    @get:PropertyName("clientId") @set:PropertyName("clientId") var clientId: String = "",
    @get:PropertyName("cliente") @set:PropertyName("cliente") var cliente: String = "",
    @get:PropertyName("vehiculo") @set:PropertyName("vehiculo") var vehiculo: String = "",
    @get:PropertyName("patente") @set:PropertyName("patente") var patente: String = "",
    @get:PropertyName("descripcion") @set:PropertyName("descripcion") var descripcion: String = "",
    
    @get:PropertyName("status") @set:PropertyName("status") var status: String = OrderStatus.NUEVA.name,
    
    @get:PropertyName("imagenUri") @set:PropertyName("imagenUri") var imagenUri: String? = null,
    
    @get:PropertyName("fechaRegistro") @set:PropertyName("fechaRegistro") var fechaRegistro: Long? = null,
    
    @get:PropertyName("arreglos") @set:PropertyName("arreglos") var arreglos: List<Arreglo> = emptyList()
) {
    @get:Exclude
    val costoTotal: Double
        get() = arreglos.sumOf { it.precio }

    @get:Exclude
    val statusEnum: OrderStatus
        get() = try {
            OrderStatus.valueOf(status)
        } catch (e: IllegalArgumentException) {
            OrderStatus.NUEVA
        }
}
