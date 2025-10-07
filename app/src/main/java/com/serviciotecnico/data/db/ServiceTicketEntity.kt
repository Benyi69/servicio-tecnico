package com.serviciotecnico.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ordenes_servicio")
data class ServiceTicketEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val cliente: String,
    val vehiculo: String,
    val patente: String,
    val descripcion: String,
    val completado: Boolean = false,
    val imagenUri: String? = null,
    val fechaRegistro: Long = System.currentTimeMillis()
)
