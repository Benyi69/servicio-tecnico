package com.serviciotecnico.data.repository

import com.serviciotecnico.data.db.ServiceDao
import com.serviciotecnico.data.db.ServiceTicketEntity
import com.serviciotecnico.model.ServiceTicket
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ServiceRepository(private val dao: ServiceDao) {

    suspend fun insertTicket(entity: ServiceTicketEntity): Long {
        return dao.insert(entity)
    }

    fun getAllTicketsFlow(): Flow<List<ServiceTicket>> {
        return dao.getAllFlow().map { list ->
            list.map { e ->
                ServiceTicket(
                    id = e.id,
                    clienteNombre = e.clienteNombre,
                    telefono = e.telefono,
                    tipoVehiculo = e.tipoVehiculo,
                    marca = e.marca,
                    modelo = e.modelo,
                    patente = e.patente,
                    descripcion = e.descripcion,
                    fotoUri = e.fotoUri,
                    estado = e.estado
                )
            }
        }
    }
}
