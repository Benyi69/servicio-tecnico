package com.serviciotecnico.data.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.serviciotecnico.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class ServiceRepository() : IServiceRepository {

    private val auth: FirebaseAuth = Firebase.auth
    private val db: FirebaseFirestore = Firebase.firestore

    override fun obtenerTodos(user: User?): Flow<List<ServiceTicket>> {
        if (user == null) {
            return flowOf(emptyList())
        }
        return callbackFlow {
            val query = when (user.role) {
                UserRole.ADMIN, UserRole.TECNICO -> db.collection("tickets")
                UserRole.CLIENTE -> db.collection("tickets").whereEqualTo("clientId", user.id)
                else -> null
            }

            val listener = query?.addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.w("ServiceRepository", "Listen failed.", e)
                    close(e)
                    return@addSnapshotListener
                }
                val tickets = snapshot?.toObjects(ServiceTicket::class.java) ?: emptyList()
                trySend(tickets)
            }
            awaitClose { listener?.remove() }
        }.flowOn(Dispatchers.IO)
    }

    override fun obtenerPorId(id: String): Flow<ServiceTicket> = callbackFlow {
        val listener = db.collection("tickets").document(id).addSnapshotListener { snapshot, e ->
            if (e != null) {
                close(e)
                return@addSnapshotListener
            }
            val ticket = snapshot?.toObject(ServiceTicket::class.java)
            if (ticket != null) {
                trySend(ticket)
            }
        }
        awaitClose { listener.remove() }
    }.flowOn(Dispatchers.IO)

    override suspend fun insertar(ticket: ServiceTicket, clientId: String): String = withContext(Dispatchers.IO) {
        val newDocRef = db.collection("tickets").document()
        val newId = newDocRef.id
        val ticketToSave = ticket.copy(
            id = newId, 
            clientId = clientId, 
            status = OrderStatus.NUEVA.name,
            fechaRegistro = System.currentTimeMillis() // Guardar como Long
        )
        newDocRef.set(ticketToSave).await()
        return@withContext newId
    }

    override suspend fun actualizarEstado(ticketId: String, nuevoEstado: OrderStatus) {
        withContext(Dispatchers.IO) {
            db.collection("tickets").document(ticketId).update("status", nuevoEstado.name).await()
        }
    }

    override suspend fun addArregloToTicket(ticketId: String, arreglo: Arreglo) {
        withContext(Dispatchers.IO) {
            val ticketDocRef = db.collection("tickets").document(ticketId)
            val arregloMap = mapOf(
                "id" to System.currentTimeMillis(),
                "descripcion" to arreglo.descripcion,
                "precio" to arreglo.precio,
                "ticketId" to ticketId
            )
            ticketDocRef.update("arreglos", FieldValue.arrayUnion(arregloMap)).await()
        }
    }

    override suspend fun eliminarTicket(ticketId: String) {
        withContext(Dispatchers.IO) {
            db.collection("tickets").document(ticketId).delete().await()
        }
    }

    override suspend fun login(email: String, pass: String): User? = withContext(Dispatchers.IO) {
        try {
            val result = auth.signInWithEmailAndPassword(email, pass).await()
            val firebaseUser = result.user ?: return@withContext null
            val userDoc = db.collection("users").document(firebaseUser.uid).get().await()
            val role = userDoc.getString("role")?.let { UserRole.valueOf(it) } ?: UserRole.CLIENTE
            val name = userDoc.getString("name") ?: firebaseUser.displayName ?: "Usuario"
            val phone = userDoc.getString("phone")
            return@withContext User(firebaseUser.uid, name, firebaseUser.email, phone, true, role)
        } catch (e: Exception) {
            return@withContext null
        }
    }

    override suspend fun register(username: String, email: String, pass: String): User? = withContext(Dispatchers.IO) {
        try {
            val result = auth.createUserWithEmailAndPassword(email, pass).await()
            val firebaseUser = result.user ?: return@withContext null
            val userMap = hashMapOf(
                "id" to firebaseUser.uid,
                "name" to username,
                "email" to email,
                "phone" to null,
                "role" to UserRole.CLIENTE.name,
                "isRegistered" to true
            )
            db.collection("users").document(firebaseUser.uid).set(userMap).await()
            return@withContext User(firebaseUser.uid, username, email, null, true, UserRole.CLIENTE)
        } catch (e: Exception) {
            return@withContext null
        }
    }

    override suspend fun recuperarPass(email: String) {
        withContext(Dispatchers.IO) {
            auth.sendPasswordResetEmail(email).await()
        }
    }

    override suspend fun actualizarPerfil(nombre: String): User? = withContext(Dispatchers.IO) {
        try {
            val firebaseUser = auth.currentUser ?: return@withContext null
            db.collection("users").document(firebaseUser.uid).update("name", nombre).await()
            val profileUpdates = userProfileChangeRequest { displayName = nombre }
            firebaseUser.updateProfile(profileUpdates).await()
            val updatedUserDoc = db.collection("users").document(firebaseUser.uid).get().await()
            val role = updatedUserDoc.getString("role")?.let { UserRole.valueOf(it) } ?: UserRole.CLIENTE
            val phone = updatedUserDoc.getString("phone")
            return@withContext User(firebaseUser.uid, nombre, firebaseUser.email, phone, true, role)
        } catch (e: Exception) {
            return@withContext null
        }
    }

    override suspend fun signOut() {
        auth.signOut()
    }

    override suspend fun obtenerClientes(): List<Cliente> = withContext(Dispatchers.IO) {
        try {
            val registeredClients = db.collection("users").whereEqualTo("role", UserRole.CLIENTE.name).get().await().toObjects(User::class.java)
            val guestClients = db.collection("guest_clients").get().await().toObjects(GuestClient::class.java)
            return@withContext registeredClients + guestClients
        } catch (e: Exception) {
            return@withContext emptyList()
        }
    }

    override suspend fun registrarClienteInvitado(name: String, email: String?, phone: String?): Cliente? = withContext(Dispatchers.IO) {
        try {
            val newGuestClientId = db.collection("guest_clients").document().id
            val guestClientMap = hashMapOf(
                "id" to newGuestClientId,
                "name" to name,
                "email" to email,
                "phone" to phone,
                "isRegistered" to false,
                "role" to UserRole.CLIENTE.name
            )
            db.collection("guest_clients").document(newGuestClientId).set(guestClientMap).await()
            return@withContext GuestClient(newGuestClientId, name, email, phone)
        } catch (e: Exception) {
            return@withContext null
        }
    }

    override fun obtenerServiceOfferings(): Flow<List<ServiceOffering>> = callbackFlow {
        val listener = db.collection("service_offerings").addSnapshotListener { snapshot, e ->
            if (e != null) {
                close(e)
                return@addSnapshotListener
            }
            val offerings = snapshot?.toObjects(ServiceOffering::class.java) ?: emptyList()
            trySend(offerings)
        }
        awaitClose { listener.remove() }
    }.flowOn(Dispatchers.IO)

    override suspend fun obtenerOrdenPorIdPublico(orderId: String): ServiceTicket? = withContext(Dispatchers.IO) {
        try {
            return@withContext db.collection("tickets").document(orderId).get().await().toObject(ServiceTicket::class.java)
        } catch (e: Exception) {
            return@withContext null
        }
    }
}
