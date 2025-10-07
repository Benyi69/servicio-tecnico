package com.serviciotecnico.model

data class Task (
    val id: Long = 0,
    val titulo: String,
    val descripcion: String,
    val completada: Boolean = false,
    val fechaCreacion: Long = System.currentTimeMillis()
)