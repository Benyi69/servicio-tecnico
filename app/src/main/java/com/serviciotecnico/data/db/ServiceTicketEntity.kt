package com.serviciotecnico.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "service_tickets")
data class ServiceTicketEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
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
