package com.serviciotecnico.model

import com.google.firebase.firestore.PropertyName

data class ServiceOffering @JvmOverloads constructor(
    @get:PropertyName("id") @set:PropertyName("id") var id: String = "",
    @get:PropertyName("name") @set:PropertyName("name") var name: String = "",
    @get:PropertyName("description") @set:PropertyName("description") var description: String = "",
    @get:PropertyName("priceApprox") @set:PropertyName("priceApprox") var priceApprox: Double = 0.0,
    @get:PropertyName("category") @set:PropertyName("category") var category: String = ""
)
