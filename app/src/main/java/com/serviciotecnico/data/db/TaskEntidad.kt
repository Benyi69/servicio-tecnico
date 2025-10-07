package com.serviciotecnico.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

// Entidad que se guarda físicamente en la base de datos local (SQLite)
@Entity(tableName = "tareas")
data class TaskEntidad(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val titulo: String,
    val descripcion: String,
    val completada: Boolean = false,
    val fechaCreacion: Long = System.currentTimeMillis()
)
