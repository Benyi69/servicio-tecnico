package com.serviciotecnico.data.db

import androidx.room.Embedded
import androidx.room.Relation

data class TicketConArreglos(
    // @Embedded le dice a Room que trate los campos de ServiceTicketEntity como si
    // fueran campos directos de esta clase.
    @Embedded val ticket: ServiceTicketEntity,

    // @Relation define la relación uno-a-muchos.
    @Relation(
        parentColumn = "id", // La columna de la tabla padre (ServiceTicketEntity)
        entityColumn = "ticketId" // La columna de la tabla hija (ArregloEntity) que hace referencia al padre
    )
    val arreglos: List<ArregloEntity>
)
