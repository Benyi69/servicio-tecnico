package com.serviciotecnico.data.db

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Entidad que representa un arreglo en la base de datos.
 * Se define una clave foránea para asegurar que cada arreglo esté siempre
 * asociado a una orden de servicio válida.
 */
@Entity(
    tableName = "arreglos",
    foreignKeys = [ForeignKey(
        entity = ServiceTicketEntity::class,
        parentColumns = ["id"],
        childColumns = ["ticketId"],
        onDelete = ForeignKey.CASCADE // Si se borra la orden, se borran sus arreglos.
    )],
    // AÑADIDO: Se crea un índice en la columna de la clave foránea.
    // Esto es crucial para el rendimiento de las consultas y para que Room pueda validar la estructura.
    indices = [Index(value = ["ticketId"])]
)
data class ArregloEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val descripcion: String,
    val precio: Double,
    val ticketId: Long
)
