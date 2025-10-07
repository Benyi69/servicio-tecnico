package com.serviciotecnico.data.repository

import com.serviciotecnico.data.db.TaskDao
import com.serviciotecnico.data.db.TaskEntidad
import com.serviciotecnico.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// El repositorio sirve de "puente" entre la base de datos y el ViewModel
class TaskRepository(private val dao: TaskDao) {

    suspend fun insertar(task: TaskEntidad): Long = dao.insertar(task)

    suspend fun actualizar(task: TaskEntidad) = dao.actualizar(task)

    suspend fun eliminar(task: TaskEntidad) = dao.eliminar(task)

    fun obtenerTodas(): Flow<List<Task>> = dao.obtenerTodas().map { lista ->
        lista.map { e ->
            Task(e.id, e.titulo, e.descripcion, e.completada, e.fechaCreacion)
        }
    }

    suspend fun obtenerPorId(id: Long): Task? = dao.obtenerPorId(id)?.let { e ->
        Task(e.id, e.titulo, e.descripcion, e.completada, e.fechaCreacion)
    }
}
