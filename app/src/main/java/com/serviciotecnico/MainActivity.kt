package com.serviciotecnico

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.serviciotecnico.data.NetworkMonitor
import com.serviciotecnico.data.repository.ServiceRepository
import com.serviciotecnico.ui.navigation.AppNavigation
import com.serviciotecnico.ui.theme.ServicioTecnicoTheme
import com.serviciotecnico.viewmodel.ServiceViewModel

class MainActivity : ComponentActivity() {

    private val vm: ServiceViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val repo = ServiceRepository()
                val networkMonitor = NetworkMonitor(applicationContext)
                return ServiceViewModel(repo, networkMonitor) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ServicioTecnicoTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    AppNavigation(navController = navController, vm = vm)
                }
            }
        }
    }
}
