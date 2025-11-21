package com.serviciotecnico.data.db

import androidx.room.Embedded
import androidx.room.Relation

data class TicketConArreglos(
    @Embedded val ticket: ServiceTicketEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "ticketId" // COMA AÑADIDA
    )
    val arreglos: List<ArregloEntity>
)
