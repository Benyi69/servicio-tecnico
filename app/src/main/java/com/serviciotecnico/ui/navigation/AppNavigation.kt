package com.serviciotecnico.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.serviciotecnico.ui.screens.*
import com.serviciotecnico.viewmodel.ServiceViewModel

@Composable
fun AppNavigation(navController: NavHostController, vm: ServiceViewModel) {
    NavHost(
        navController, 
        startDestination = "login",
    ) {
        
        composable("login") { 
            LoginScreen(navController, vm) 
        }
        
        composable("lista") { 
            AdminDashboardScreen(navController, vm) 
        }

        composable("cliente_dashboard") {
            ClienteDashboardScreen(navController, vm)
        }

        composable("seleccionar_cliente") {
            SeleccionarClienteScreen(navController, vm)
        }

        composable("registrar_cliente_invitado") {
            RegistrarClienteInvitadoScreen(navController, vm)
        }
        
        composable(
            route = "registro_orden?clienteId={clienteId}&clienteNombre={clienteNombre}",
            arguments = listOf(
                navArgument("clienteId") { type = NavType.StringType; nullable = true },
                navArgument("clienteNombre") { type = NavType.StringType; nullable = true }
            )
        ) { backStackEntry ->
            val clienteId = backStackEntry.arguments?.getString("clienteId")
            val clienteNombre = backStackEntry.arguments?.getString("clienteNombre")
            RegistroOrdenScreen(navController, vm, clienteId, clienteNombre)
        }
        
        composable("registro_usuario") { 
            RegistroUsuarioScreen(navController, vm)
        }

        composable("recuperar_pass") {
            RecuperarPassScreen(navController, vm)
        }

        composable("perfil") {
            PerfilScreen(navController, vm)
        }

        composable("track_order") {
            TrackOrderScreen(navController, vm)
        }
        
        composable(
            route = "detalle_orden/{id}", 
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: ""
            DetalleOrdenScreen(navController, vm, id)
        }
    }
}
