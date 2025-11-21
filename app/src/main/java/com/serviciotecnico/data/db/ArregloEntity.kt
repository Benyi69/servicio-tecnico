package com.serviciotecnico.data.db

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "arreglos",
    foreignKeys = [ForeignKey(
        entity = ServiceTicketEntity::class,
        parentColumns = ["id"],
        childColumns = ["ticketId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["ticketId"])]
)
data class ArregloEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val descripcion: String,
    val precio: Double,
    val ticketId: Long
)
