package com.serviciotecnico.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serviciotecnico.data.db.TaskEntidad
import com.serviciotecnico.data.repository.TaskRepository
import com.serviciotecnico.model.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class TaskFormState(
    val title: String = "",
    val description: String = ""
)

data class TaskFormErrors(
    val titleError: String? = null
)


class TaskViewModel(private val repo: TaskRepository) : ViewModel() {

    private val _form = MutableStateFlow(TaskFormState())
    val form: StateFlow<TaskFormState> = _form.asStateFlow()

    private val _errors = MutableStateFlow(TaskFormErrors())
    val errors: StateFlow<TaskFormErrors> = _errors.asStateFlow()

    val tasks = repo.obtenerTodas().stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    private val _editingId = MutableStateFlow(0L)
    val editingId: StateFlow<Long> = _editingId.asStateFlow()

    fun setTitle(value: String) = _form.update { it.copy(title = value) }
    fun setDescription(value: String) = _form.update { it.copy(description = value) }

    private fun validateTitle(): Boolean {
        val titleValue = _form.value.title.trim()
        val error = when {
            titleValue.isEmpty() -> "El título es requerido"
            titleValue.length < 3 -> "El título es demasiado corto"
            else -> null
        }
        _errors.update { it.copy(titleError = error) }
        return error == null
    }

    /**
     * Guarda o actualiza una tarea.
     * Realiza la operación de base de datos en un hilo de fondo (IO) y actualiza
     * el estado de la UI en el hilo principal (Main).
     */
    fun saveTask(onComplete: (Long?) -> Unit = {}) {
        if (!validateTitle()) {
            onComplete(null)
            return
        }

        // Captura el estado actual para garantizar la consistencia en la corrutina.
        val currentForm = _form.value
        val currentEditingId = _editingId.value

        viewModelScope.launch(Dispatchers.IO) {
            val taskEntity = TaskEntidad(
                id = currentEditingId,
                titulo = currentForm.title,
                descripcion = currentForm.description
            )

            val savedId = if (currentEditingId == 0L) {
                repo.insertar(taskEntity) // Insert devuelve el nuevo ID.
            } else {
                repo.actualizar(taskEntity)
                currentEditingId // En una actualización, devolvemos el ID existente.
            }

            // Cambia de vuelta al hilo principal para actualizar el estado de la UI.
            withContext(Dispatchers.Main) {
                resetForm()
                onComplete(savedId)
            }
        }
    }

    /**
     * Restablece el formulario a su estado inicial.
     */
    private fun resetForm() {
        _form.value = TaskFormState()
        _editingId.value = 0L
    }

    fun deleteTask(task: Task, onComplete: () -> Unit = {}) {
        viewModelScope.launch(Dispatchers.IO) {
            val taskEntity = TaskEntidad(
                id = task.id,
                titulo = task.titulo,
                descripcion = task.descripcion,
                completada= task.completada,
                fechaCreacion = task.fechaCreacion
            )
            repo.eliminar(taskEntity)
            withContext(Dispatchers.Main) {
                onComplete()
            }

            onComplete()
        }
    }
}
