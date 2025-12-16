package com.serviciotecnico.model

import com.google.firebase.firestore.PropertyName

data class GuestClient @JvmOverloads constructor(
    @get:PropertyName("id") @set:PropertyName("id") override var id: String = "",
    @get:PropertyName("name") @set:PropertyName("name") override var name: String = "",
    @get:PropertyName("email") @set:PropertyName("email") override var email: String? = null,
    @get:PropertyName("phone") @set:PropertyName("phone") override var phone: String? = null,
    @get:PropertyName("isRegistered") @set:PropertyName("isRegistered") override var isRegistered: Boolean = false,
    @get:PropertyName("role") @set:PropertyName("role") override var role: UserRole = UserRole.CLIENTE
) : Cliente
