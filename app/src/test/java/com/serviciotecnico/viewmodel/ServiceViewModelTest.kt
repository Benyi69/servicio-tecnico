package com.serviciotecnico.viewmodel

import com.serviciotecnico.data.INetworkMonitor
import com.serviciotecnico.data.repository.IServiceRepository
import com.serviciotecnico.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class ServiceViewModelTest {

    private val testDispatcher = StandardTestDispatcher()



    class FakeServiceRepository : IServiceRepository {
        private val testUser = User("1", "test", "test@test.com", null, true, UserRole.TECNICO)
        
        override suspend fun login(email: String, pass: String): User? {
            return if (email == "test@test.com" && pass == "password") {
                testUser
            } else {
                null
            }
        }

        // Implementaciones actualizadas de los métodos
        override fun obtenerTodos(user: User?): Flow<List<ServiceTicket>> = flowOf(emptyList())
        override fun obtenerPorId(id: String): Flow<ServiceTicket> = flowOf(ServiceTicket(id = id))
        override suspend fun insertar(ticket: ServiceTicket, clientId: String): String = "new_ticket_id"
        override suspend fun actualizarEstado(ticketId: String, nuevoEstado: OrderStatus) {}
        override suspend fun addArregloToTicket(ticketId: String, arreglo: Arreglo) {}
        override suspend fun eliminarTicket(ticketId: String) {}
        override suspend fun register(username: String, email: String, pass: String): User? = null
        override suspend fun recuperarPass(email: String) {}
        override suspend fun actualizarPerfil(nombre: String): User? = null
        override suspend fun signOut() {}
        override suspend fun obtenerClientes(): List<Cliente> = emptyList()
        override suspend fun registrarClienteInvitado(name: String, email: String?, phone: String?): Cliente? = null
        override suspend fun obtenerOrdenPorIdPublico(orderId: String): ServiceTicket? = null
        override fun obtenerServiceOfferings(): Flow<List<ServiceOffering>> = flowOf(emptyList())
    }

    class FakeNetworkMonitor : INetworkMonitor {
        override val isOnline = MutableStateFlow(true)
        override fun unregister() {}
    }

    // --- Tests ---

    private lateinit var viewModel: ServiceViewModel
    private lateinit var fakeRepository: FakeServiceRepository
    private lateinit var fakeNetworkMonitor: FakeNetworkMonitor

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        fakeRepository = FakeServiceRepository()
        fakeNetworkMonitor = FakeNetworkMonitor()
        viewModel = ServiceViewModel(fakeRepository, fakeNetworkMonitor)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `login con credenciales correctas actualiza el estado a exitoso`() = runTest {
        // Given
        val email = "test@test.com"
        val password = "password"

        // When
        viewModel.login(email, password)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assertEquals(true, viewModel.loginExitoso.value)
        assertNotNull(viewModel.uiState.value.currentUser)
        assertEquals(email, viewModel.uiState.value.currentUser?.email)
    }

    @Test
    fun `login con credenciales incorrectas actualiza el estado a error`() = runTest {
        // Given
        val email = "test@test.com"
        val password = "wrongpassword"

        // When
        viewModel.login(email, password)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assertEquals(false, viewModel.loginExitoso.value)
        assertNull(viewModel.uiState.value.currentUser)
        assertEquals("Credenciales inválidas", viewModel.uiState.value.error)
    }
}
