package com.serviciotecnico.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serviciotecnico.data.db.ServiceTicketEntity
import com.serviciotecnico.data.repository.ServiceRepository
import com.serviciotecnico.model.Arreglo
import com.serviciotecnico.model.ErrorFormularioOrden
import com.serviciotecnico.model.EstadoFormularioOrden
import com.serviciotecnico.model.ServiceTicket
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class ServiceUiState(
    val tickets: List<ServiceTicket> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class ServiceViewModel(private val repo: ServiceRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(ServiceUiState(isLoading = true))
    val uiState: StateFlow<ServiceUiState> = _uiState.asStateFlow()

    private val _formulario = MutableStateFlow(EstadoFormularioOrden())
    val formulario: StateFlow<EstadoFormularioOrden> = _formulario.asStateFlow()

    private val _errores = MutableStateFlow(ErrorFormularioOrden())
    val errores: StateFlow<ErrorFormularioOrden> = _errores.asStateFlow()

    private val _ticketDetalle = MutableStateFlow<ServiceTicket?>(null)
    val ticketDetalle: StateFlow<ServiceTicket?> = _ticketDetalle.asStateFlow()

    private val _loginExitoso = MutableStateFlow(false)
    val loginExitoso: StateFlow<Boolean> = _loginExitoso.asStateFlow()

    init {
        viewModelScope.launch {
            repo.obtenerTodos().collect { tickets ->
                _uiState.update { it.copy(tickets = tickets, isLoading = false) }
            }
        }
        // Llamada a sincronizar que faltaba
        sincronizar()
    }

    fun login(usuario: String, clave: String) {
        if (usuario == "admin" && clave == "admin") {
            _loginExitoso.value = true
        }
    }

    // FUNCIÓN QUE FALTABA
    fun sincronizar() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                repo.sincronizarTickets()
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Error de sincronización: ${e.message}") }
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }
    
    fun obtenerTicketPorId(id: Long) {
        viewModelScope.launch {
            repo.obtenerPorId(id).collect { ticket ->
                _ticketDetalle.value = ticket
            }
        }
    }

    fun actualizarEstadoTicket(ticket: ServiceTicket, completado: Boolean) {
        viewModelScope.launch {
            val ticketActualizado = ticket.copy(completado = completado)
            repo.actualizar(ticketActualizado)
        }
    }

    fun insertarArreglo(ticketId: Long, descripcion: String, precio: Double) {
        viewModelScope.launch {
            val nuevoArreglo = Arreglo(ticketId = ticketId, descripcion = descripcion, precio = precio)
            repo.insertarArreglo(nuevoArreglo)
        }
    }

    fun eliminarArreglo(arreglo: Arreglo) {
        viewModelScope.launch {
            repo.eliminarArreglo(arreglo)
        }
    }

    // FUNCIÓN QUE FALTABA
    fun eliminarTicket(ticket: ServiceTicket) {
        viewModelScope.launch {
            val entidad = ServiceTicketEntity(
                id = ticket.id,
                cliente = ticket.cliente,
                vehiculo = ticket.vehiculo,
                patente = ticket.patente,
                descripcion = ticket.descripcion,
                completado = ticket.completado,
                imagenUri = ticket.imagenUri,
                fechaRegistro = ticket.fechaRegistro
            )
            repo.eliminar(entidad)
        }
    }

    fun setCliente(valor: String) = _formulario.update { it.copy(cliente = valor) }
    fun setVehiculo(valor: String) = _formulario.update { it.copy(vehiculo = valor) }
    fun setPatente(valor: String) = _formulario.update { it.copy(patente = valor) }
    fun setDescripcion(valor: String) = _formulario.update { it.copy(descripcion = valor) }
    fun setImagen(uri: String?) = _formulario.update { it.copy(imagenUri = uri) }

    private fun validar(): Boolean {
        val f = _formulario.value
        val erroresNuevos = ErrorFormularioOrden(
            errorCliente = if (f.cliente.isBlank()) "Debe ingresar el nombre del cliente" else null,
            errorVehiculo = if (f.vehiculo.isBlank()) "Debe indicar el vehículo" else null,
            errorPatente = if (f.patente.isBlank()) "Debe ingresar la patente" else null,
            errorDescripcion = if (f.descripcion.isBlank()) "Debe describir el trabajo" else null
        )
        _errores.value = erroresNuevos
        return erroresNuevos.errorCliente == null && erroresNuevos.errorVehiculo == null && erroresNuevos.errorPatente == null && erroresNuevos.errorDescripcion == null
    }

    fun guardarTicket(onComplete: (Long) -> Unit) {
        if (!validar()) { return }
        viewModelScope.launch {
            val f = _formulario.value
            val nuevoTicket = ServiceTicket(
                cliente = f.cliente,
                vehiculo = f.vehiculo,
                patente = f.patente,
                descripcion = f.descripcion
            )
            val nuevoId = repo.insertar(nuevoTicket)
            _formulario.value = EstadoFormularioOrden()
            onComplete(nuevoId)
        }
    }
}
