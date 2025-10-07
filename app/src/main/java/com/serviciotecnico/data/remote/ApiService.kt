package com.serviciotecnico.data.remote

import com.serviciotecnico.model.ServiceTicket
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * Interfaz que define los endpoints de la API del microservicio.
 */
interface ApiService {

    /**
     * Obtiene la lista completa de tickets de servicio desde el servidor.
     */
    @GET("tickets")
    suspend fun getAllTickets(): Response<List<ServiceTicket>>

    /**
     * Envía un nuevo ticket de servicio al servidor para ser guardado.
     * @param ticket El objeto del ticket a guardar.
     * @return El ticket guardado, posiblemente con el ID asignado por el servidor.
     */
    @POST("tickets")
    suspend fun saveTicket(@Body ticket: ServiceTicket): Response<ServiceTicket>
}
