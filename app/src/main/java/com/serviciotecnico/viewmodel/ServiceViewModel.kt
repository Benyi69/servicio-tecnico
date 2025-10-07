package com.serviciotecnico.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serviciotecnico.data.repository.ServiceRepository
import com.serviciotecnico.data.db.ServiceTicketEntity
import com.serviciotecnico.model.TicketFormErrors
import com.serviciotecnico.model.TicketFormState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ServiceViewModel(private val repo: ServiceRepository, context: Context? = null) : ViewModel() {

    private val _form = MutableStateFlow(TicketFormState())
    val form: StateFlow<TicketFormState> = _form.asStateFlow()

    private val _errors = MutableStateFlow(TicketFormErrors())
    val errors: StateFlow<TicketFormErrors> = _errors.asStateFlow()

    val tickets = repo.getAllTicketsFlow()
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    fun setClienteNombre(v: String) = _form.update { it.copy(clienteNombre = v) }
    fun setTelefono(v: String) = _form.update { it.copy(telefono = v) }
    fun setTipoVehiculo(v: String) = _form.update { it.copy(tipoVehiculo = v) }
    fun setMarca(v: String) = _form.update { it.copy(marca = v) }
    fun setModelo(v: String) = _form.update { it.copy(modelo = v) }
    fun setPatente(v: String) = _form.update { it.copy(patente = v) }
    fun setDescripcion(v: String) = _form.update { it.copy(descripcion = v) }
    fun setFotoUri(v: String?) = _form.update { it.copy(fotoUri = v) }

    private fun validateNombre(): Boolean {
        val n = _form.value.clienteNombre.trim()
        val err = when {
            n.isEmpty() -> "Nombre obligatorio"
            n.length < 3 -> "Nombre muy corto"
            else -> null
        }
        _errors.update { it.copy(clienteNombreError = err) }
        return err == null
    }

    private fun validateTelefono(): Boolean {
        val t = _form.value.telefono.trim()
        val err = when {
            t.isEmpty() -> "Teléfono obligatorio"
            t.length < 7 -> "Teléfono inválido"
            else -> null
        }
        _errors.update { it.copy(telefonoError = err) }
        return err == null
    }

    private fun validatePatente(): Boolean {
        val p = _form.value.patente.trim()
        val err = if (p.isEmpty()) "Patente obligatoria" else null
        _errors.update { it.copy(patenteError = err) }
        return err == null
    }

    private fun validateDescripcion(): Boolean {
        val d = _form.value.descripcion.trim()
        val err = if (d.isEmpty()) "Describe el problema" else null
        _errors.update { it.copy(descripcionError = err) }
        return err == null
    }

    fun validarFormulario(): Boolean {
        val v1 = validateNombre()
        val v2 = validateTelefono()
        val v3 = validatePatente()
        val v4 = validateDescripcion()
        return v1 && v2 && v3 && v4
    }

    fun submitTicket(onComplete: (Long?) -> Unit = {}) {
        if (!validarFormulario()) { onComplete(null); return }
        val f = _form.value
        val entity = ServiceTicketEntity(
            clienteNombre = f.clienteNombre,
            telefono = f.telefono,
            tipoVehiculo = f.tipoVehiculo,
            marca = f.marca,
            modelo = f.modelo,
            patente = f.patente,
            descripcion = f.descripcion,
            fotoUri = f.fotoUri
        )
        viewModelScope.launch {
            val id = repo.insertTicket(entity)
            _form.value = TicketFormState()
            _errors.value = TicketFormErrors()
            onComplete(id)
        }
    }
}
