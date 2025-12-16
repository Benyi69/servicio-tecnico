package com.serviciotecnico.model

enum class OrderStatus(val displayName: String) {
    NUEVA("Nueva"),
    EN_DIAGNOSTICO("En Diagnóstico"),
    ESPERANDO_APROBACION("Esperando Aprobación"),
    APROBADA("Aprobada"),
    EN_REPARACION("En Reparación"),
    COMPLETADA("Completada"),
    RECHAZADA("Rechazada")
}
