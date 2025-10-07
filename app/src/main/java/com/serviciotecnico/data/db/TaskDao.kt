package com.serviciotecnico.data.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

// DAO: Define las operaciones que se pueden hacer con la base de datos
@Dao
interface TaskDao {

    @Insert suspend fun insertar(tarea: TaskEntidad): Long

    @Update suspend fun actualizar(tarea: TaskEntidad)

    @Delete suspend fun eliminar(tarea: TaskEntidad)

    @Query("SELECT * FROM tareas ORDER BY fechaCreacion DESC")
    fun obtenerTodas(): Flow<List<TaskEntidad>>

    @Query("SELECT * FROM tareas WHERE id = :id LIMIT 1")
    suspend fun obtenerPorId(id: Long): TaskEntidad?
}
