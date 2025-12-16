package com.serviciotecnico.data.repository

import com.serviciotecnico.model.*
import kotlinx.coroutines.flow.Flow

interface IServiceRepository {
    fun obtenerTodos(user: User?): Flow<List<ServiceTicket>>
    fun obtenerPorId(id: String): Flow<ServiceTicket>
    suspend fun insertar(ticket: ServiceTicket, clientId: String): String
    suspend fun actualizarEstado(ticketId: String, nuevoEstado: OrderStatus)
    suspend fun addArregloToTicket(ticketId: String, arreglo: Arreglo)
    suspend fun eliminarTicket(ticketId: String)
    suspend fun login(email: String, pass: String): User?
    suspend fun register(username: String, email: String, pass: String): User?
    suspend fun recuperarPass(email: String)
    suspend fun actualizarPerfil(nombre: String): User?
    suspend fun signOut()
    suspend fun obtenerClientes(): List<Cliente>
    suspend fun registrarClienteInvitado(name: String, email: String?, phone: String?): Cliente?
    suspend fun obtenerOrdenPorIdPublico(orderId: String): ServiceTicket?
    fun obtenerServiceOfferings(): Flow<List<ServiceOffering>>
}
