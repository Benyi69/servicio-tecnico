package com.serviciotecnico.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ServiceDao {
    @Insert
    suspend fun insert(ticket: ServiceTicketEntity): Long

    @Query("SELECT * FROM service_tickets ORDER BY id DESC")
    fun getAllFlow(): Flow<List<ServiceTicketEntity>>

    @Query("SELECT * FROM service_tickets WHERE id = :id LIMIT 1")
    suspend fun getById(id: Long): ServiceTicketEntity?
}
