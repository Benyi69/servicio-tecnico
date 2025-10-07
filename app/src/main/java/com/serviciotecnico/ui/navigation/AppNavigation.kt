package com.serviciotecnico.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.serviciotecnico.ui.screens.DetalleOrdenScreen
import com.serviciotecnico.ui.screens.ListaOrdenesScreen
import com.serviciotecnico.ui.screens.LoginScreen
import com.serviciotecnico.ui.screens.RegistroScreen
import com.serviciotecnico.viewmodel.ServiceViewModel

@Composable
fun AppNavigation(navController: NavHostController, vm: ServiceViewModel) {
    // La aplicación ahora comenzará en la pantalla de login
    NavHost(navController, startDestination = "login") {
        
        composable("login") { 
            LoginScreen(navController, vm) 
        }
        
        composable("lista") { 
            ListaOrdenesScreen(navController, vm) 
        }
        
        composable("registro") { 
            RegistroScreen(navController, vm) 
        }
        
        composable(
            route = "detalle_orden/{id}", 
            arguments = listOf(navArgument("id") { type = NavType.LongType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getLong("id") ?: 0L
            DetalleOrdenScreen(navController, vm, id)
        }
    }
}
