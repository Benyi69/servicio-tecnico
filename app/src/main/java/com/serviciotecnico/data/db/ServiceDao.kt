package com.serviciotecnico.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ServiceDao {

    @Transaction
    @Query("SELECT * FROM ordenes_servicio ORDER BY fechaRegistro DESC")
    fun obtenerTodos(): Flow<List<TicketConArreglos>>

    @Transaction
    @Query("SELECT * FROM ordenes_servicio WHERE id = :id")
    fun obtenerPorId(id: Long): Flow<TicketConArreglos?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(entidad: ServiceTicketEntity): Long

    @Update
    suspend fun actualizar(entidad: ServiceTicketEntity): Int

    @Delete
    suspend fun eliminar(entidad: ServiceTicketEntity): Int

    @Query("DELETE FROM ordenes_servicio")
    suspend fun eliminarTodos()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarArreglo(arreglo: ArregloEntity): Long

    @Delete
    suspend fun eliminarArreglo(arreglo: ArregloEntity): Int
}
