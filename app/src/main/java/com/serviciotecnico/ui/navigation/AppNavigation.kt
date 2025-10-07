package com.serviciotecnico.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.serviciotecnico.ui.screens.RegistroScreen
import com.serviciotecnico.ui.screens.TicketListScreen
import com.serviciotecnico.viewmodel.ServiceViewModel

@Composable
fun AppNavigation(navController: NavHostController, vm: ServiceViewModel) {
    NavHost(navController = navController, startDestination = "lista") {
        composable("lista") {
            TicketListScreen(navController = navController, vm = vm)
        }
        composable("registro") {
            RegistroScreen(navController = navController, vm = vm)
        }
    }
}
