package com.serviciotecnico.data

import kotlinx.coroutines.flow.StateFlow

interface INetworkMonitor {
    val isOnline: StateFlow<Boolean>
    fun unregister()
}
