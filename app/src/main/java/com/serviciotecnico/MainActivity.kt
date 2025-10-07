package com.serviciotecnico

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.compose.rememberNavController
import com.serviciotecnico.data.db.AppDatabase
import com.serviciotecnico.data.repository.ServiceRepository
import com.serviciotecnico.ui.navigation.AppNavigation
import com.serviciotecnico.viewmodel.ServiceViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MainActivity : ComponentActivity() {

    private val vm: ServiceViewModel by viewModels {
        object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val db = AppDatabase.getInstance(applicationContext)
                val repo = ServiceRepository(db.serviceDao())
                return ServiceViewModel(repo, applicationContext) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            MaterialTheme {
                Surface {
                    AppNavigation(navController = navController, vm = vm)
                }
            }
        }
    }
}
