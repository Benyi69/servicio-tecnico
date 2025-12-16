package com.serviciotecnico.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serviciotecnico.data.INetworkMonitor
import com.serviciotecnico.data.repository.IServiceRepository
import com.serviciotecnico.model.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class ServiceUiState(
    val tickets: List<ServiceTicket> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val currentUser: User? = null,
    val isOnline: Boolean = true,
    val clientList: List<Cliente> = emptyList(),
    val publicTrackedTicket: ServiceTicket? = null,
    val serviceOfferings: List<ServiceOffering> = emptyList()
)

class ServiceViewModel(
    private val repo: IServiceRepository,
    private val networkMonitor: INetworkMonitor
) : ViewModel() {

    private val _uiState = MutableStateFlow(ServiceUiState(isLoading = true))
    val uiState: StateFlow<ServiceUiState> = _uiState.asStateFlow()

    private val _formulario = MutableStateFlow(EstadoFormularioOrden())
    val formulario: StateFlow<EstadoFormularioOrden> = _formulario.asStateFlow()

    private val _ticketDetalle = MutableStateFlow<ServiceTicket?>(null)
    val ticketDetalle: StateFlow<ServiceTicket?> = _ticketDetalle.asStateFlow()

    private val _loginExitoso = MutableStateFlow(false)
    val loginExitoso: StateFlow<Boolean> = _loginExitoso.asStateFlow()
    
    private val _registroExitoso = MutableStateFlow(false)
    val registroExitoso: StateFlow<Boolean> = _registroExitoso.asStateFlow()

    init {
        val userTicketsFlow = _uiState.map { it.currentUser }
            .distinctUntilChanged()
            .flatMapLatest { user ->
                repo.obtenerTodos(user)
            }

        combine(
            userTicketsFlow,
            repo.obtenerServiceOfferings(),
            networkMonitor.isOnline
        ) { tickets, offerings, isOnline ->
            _uiState.update { currentState ->
                currentState.copy(
                    tickets = tickets,
                    serviceOfferings = offerings,
                    isOnline = isOnline,
                    isLoading = false
                )
            }
        }.launchIn(viewModelScope)
    }

    override fun onCleared() {
        super.onCleared()
        networkMonitor.unregister()
    }

    fun login(email: String, clave: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            val user = repo.login(email, clave)
            if (user != null) {
                _uiState.update { it.copy(currentUser = user, isLoading = false, error = null) }
                _loginExitoso.value = true
            } else {
                _uiState.update { it.copy(error = "Credenciales inválidas", isLoading = false) }
            }
        }
    }

    fun register(username: String, email: String, pass: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val user = repo.register(username, email, pass)
                if (user != null) {
                    _uiState.update { it.copy(isLoading = false, error = null) }
                    _registroExitoso.value = true
                } else {
                    _uiState.update { it.copy(error = "Error desconocido en el registro", isLoading = false) }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message ?: "Error en el registro", isLoading = false) }
            }
        }
    }

    fun resetRegistroExitoso() {
        _registroExitoso.value = false
    }

    fun recuperarPass(email: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                repo.recuperarPass(email)
                _uiState.update { it.copy(isLoading = false, error = null) }
                onSuccess()
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }

    fun actualizarPerfil(nombre: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            val updatedUser = repo.actualizarPerfil(nombre)
            if (updatedUser != null) {
                _uiState.update { it.copy(currentUser = updatedUser, isLoading = false, error = null) }
            } else {
                _uiState.update { it.copy(isLoading = false, error = "Error al actualizar el perfil") }
            }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            repo.signOut()
            _uiState.update { it.copy(currentUser = null) }
            _loginExitoso.value = false
        }
    }

    fun obtenerClientes() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            val clients = repo.obtenerClientes()
            _uiState.update { it.copy(clientList = clients, isLoading = false) }
        }
    }

    fun registrarClienteInvitado(name: String, email: String?, phone: String?, onComplete: (Cliente?) -> Unit) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            val newClient = repo.registrarClienteInvitado(name, email, phone)
            _uiState.update { it.copy(isLoading = false) }
            onComplete(newClient)
        }
    }

    fun obtenerOrdenPorIdPublico(orderId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, publicTrackedTicket = null, error = null) }
            try {
                val ticket = repo.obtenerOrdenPorIdPublico(orderId)
                _uiState.update { it.copy(publicTrackedTicket = ticket, isLoading = false) }
                if (ticket == null) {
                    _uiState.update { it.copy(error = "Orden no encontrada.") }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Error al buscar orden: ${e.message}", isLoading = false) }
            }
        }
    }

    fun actualizarEstadoOrden(ticketId: String, nuevoEstado: OrderStatus) {
        viewModelScope.launch {
            try {
                repo.actualizarEstado(ticketId, nuevoEstado)
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Error al actualizar estado: ${e.message}") }
            }
        }
    }
    
    fun obtenerTicketPorId(id: String) {
        viewModelScope.launch {
            repo.obtenerPorId(id).collect { ticket ->
                _ticketDetalle.value = ticket
            }
        }
    }

    fun insertarArreglo(ticketId: String, descripcion: String, precio: Double) {
        viewModelScope.launch {
            try {
                val nuevoArreglo = Arreglo(ticketId = ticketId, descripcion = descripcion, precio = precio)
                repo.addArregloToTicket(ticketId, nuevoArreglo)
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Error al añadir arreglo: ${e.message}") }
            }
        }
    }

    fun eliminarTicket(ticketId: String) {
        viewModelScope.launch {
            try {
                repo.eliminarTicket(ticketId)
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Error al eliminar ticket: ${e.message}") }
            }
        }
    }

    fun setCliente(valor: String) = _formulario.update { it.copy(cliente = valor) }
    fun setVehiculo(valor: String) = _formulario.update { it.copy(vehiculo = valor) }
    fun setPatente(valor: String) = _formulario.update { it.copy(patente = valor) }
    fun setDescripcion(valor: String) = _formulario.update { it.copy(descripcion = valor) }
    fun setImagen(uri: String?) = _formulario.update { it.copy(imagenUri = uri) }

    fun guardarTicket(clientId: String, onComplete: (String) -> Unit) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val f = _formulario.value
                val nuevoTicket = ServiceTicket(
                    clientId = clientId,
                    cliente = f.cliente,
                    vehiculo = f.vehiculo,
                    patente = f.patente,
                    descripcion = f.descripcion,
                    imagenUri = f.imagenUri
                )
                val nuevoId = repo.insertar(nuevoTicket, clientId)
                _formulario.value = EstadoFormularioOrden()
                _uiState.update { it.copy(isLoading = false) }
                onComplete(nuevoId)
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = "Error al guardar la orden: ${e.message}") }
            }
        }
    }
}
