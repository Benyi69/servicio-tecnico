package com.serviciotecnico.data.repository

import android.util.Log
import com.serviciotecnico.data.db.ArregloEntity
import com.serviciotecnico.data.db.ServiceDao
import com.serviciotecnico.data.db.ServiceTicketEntity
import com.serviciotecnico.data.db.TicketConArreglos
import com.serviciotecnico.model.Arreglo
import com.serviciotecnico.model.ServiceTicket
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map

class ServiceRepository(private val dao: ServiceDao) {

    // Función privada para convertir la data de la BD al modelo de la UI
    private fun TicketConArreglos.toModel(): ServiceTicket {
        return ServiceTicket(
            id = this.ticket.id,
            cliente = this.ticket.cliente,
            vehiculo = this.ticket.vehiculo,
            patente = this.ticket.patente,
            descripcion = this.ticket.descripcion,
            completado = this.ticket.completado,
            imagenUri = this.ticket.imagenUri,
            fechaRegistro = this.ticket.fechaRegistro,
            arreglos = this.arreglos.map { Arreglo(it.id, it.descripcion, it.precio, it.ticketId) }
        )
    }

    fun obtenerTodos(): Flow<List<ServiceTicket>> = dao.obtenerTodos().map { lista ->
        lista.map { it.toModel() }
    }

    fun obtenerPorId(id: Long): Flow<ServiceTicket> = dao.obtenerPorId(id).filterNotNull().map { it.toModel() }

    suspend fun insertar(ticket: ServiceTicket): Long {
        Log.d("ServiceRepository", "Insertando nuevo ticket en BD local.")
        val entidad = ServiceTicketEntity(
            cliente = ticket.cliente,
            vehiculo = ticket.vehiculo,
            patente = ticket.patente,
            descripcion = ticket.descripcion
        )
        return dao.insertar(entidad)
    }

    suspend fun actualizar(ticket: ServiceTicket) {
        Log.d("ServiceRepository", "Actualizando ticket en BD local.")
        val entidad = ServiceTicketEntity(
            id = ticket.id,
            cliente = ticket.cliente,
            vehiculo = ticket.vehiculo,
            patente = ticket.patente,
            descripcion = ticket.descripcion,
            completado = ticket.completado,
            imagenUri = ticket.imagenUri,
            fechaRegistro = ticket.fechaRegistro
        )
        dao.actualizar(entidad)
    }

    suspend fun insertarArreglo(arreglo: Arreglo) {
        val entidad = ArregloEntity(
            descripcion = arreglo.descripcion,
            precio = arreglo.precio,
            ticketId = arreglo.ticketId
        )
        dao.insertarArreglo(entidad)
    }

    suspend fun eliminarArreglo(arreglo: Arreglo) {
        val entidad = ArregloEntity(
            id = arreglo.id,
            descripcion = arreglo.descripcion,
            precio = arreglo.precio,
            ticketId = arreglo.ticketId
        )
        dao.eliminarArreglo(entidad)
    }

    suspend fun eliminar(ticket: ServiceTicketEntity) = dao.eliminar(ticket)

    suspend fun sincronizarTickets() {
        Log.d("ServiceRepository", "MODO OFFLINE: Sincronización de red deshabilitada.")
    }
}
