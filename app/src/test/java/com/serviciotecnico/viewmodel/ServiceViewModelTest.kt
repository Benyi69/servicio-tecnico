package com.serviciotecnico.viewmodel

import app.cash.turbine.test
import com.serviciotecnico.data.repository.ServiceRepository
import com.serviciotecnico.model.ErrorFormularioOrden
import com.serviciotecnico.model.EstadoFormularioOrden
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class ServiceViewModelTest {

    private lateinit var viewModel: ServiceViewModel
    private lateinit var repository: ServiceRepository
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repository = mock {
            on { obtenerTodos() } doReturn flowOf(emptyList())
        }
        viewModel = ServiceViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `validar - cuando los campos estan vacios - deberia emitir errores`() = runTest {
        viewModel.errores.test {
            assertEquals(ErrorFormularioOrden(), awaitItem())

            viewModel.guardarTicket { /* no-op */ }

            val errores = awaitItem()
            // Lo devolvemos a su estado correcto
            assertNotNull("El error del cliente no debería ser nulo", errores.errorCliente)
            
            assertNotNull("El error del vehículo no debería ser nulo", errores.errorVehiculo)
            assertNotNull("El error de la patente no debería ser nulo", errores.errorPatente)
            assertNotNull("El error de la descripción no debería ser nulo", errores.errorDescripcion)
        }
    }

    @Test
    fun `validar - cuando todos los campos estan correctos - no deberia emitir errores`() = runTest {
        viewModel.setCliente("Nombre Valido")
        viewModel.setVehiculo("Vehiculo Valido")
        viewModel.setPatente("Patente Valida")
        viewModel.setDescripcion("Descripcion Valida")

        viewModel.errores.test {
            assertEquals(ErrorFormularioOrden(), awaitItem())

            viewModel.guardarTicket { /* no-op */ }

            expectNoEvents()
        }
    }

    @Test
    fun `guardarTicket - cuando es exitoso - deberia limpiar el formulario`() = runTest {
        whenever(repository.insertar(any())).thenReturn(1L)

        viewModel.setCliente("Test Cliente")
        viewModel.setVehiculo("Test Vehiculo")
        viewModel.setPatente("Test Patente")
        viewModel.setDescripcion("Test Descripcion")

        viewModel.formulario.test {
            val formInicial = awaitItem()
            assertEquals("Test Cliente", formInicial.cliente)

            viewModel.guardarTicket { /* no-op */ }

            val formFinal = awaitItem()
            assertEquals(EstadoFormularioOrden(), formFinal)
        }
    }
}
