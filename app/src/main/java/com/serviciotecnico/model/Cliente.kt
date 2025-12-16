package com.serviciotecnico.model

interface Cliente {
    val id: String
    val name: String
    val email: String?
    val phone: String?
    val isRegistered: Boolean
    val role: UserRole
}
