package com.example.sirma_android

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sirma_android.appointments.AppointmentDetailScreen
import com.example.sirma_android.appointments.AppointmentsScreen
import com.example.sirma_android.data.AppointmentRepository
import com.example.sirma_android.data.AuthStore
import com.example.sirma_android.data.provideApiService
import com.example.sirma_android.login.LoginScreen
import com.example.sirma_android.login.LoginViewModel
import com.example.sirma_android.login.LoginViewModelFactory
import com.example.sirma_android.ui.theme.SIRMAAndroidTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("ViewModelConstructorInComposable")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SIRMAAndroidTheme {
                // Inicializa NavController
                val navController = rememberNavController()

                // MainActivity.kt
                // MainActivity.kt
                val apiService = provideApiService() // Obtén la instancia de ApiService
                // Obtén la instancia de ApiService
                val appointmentRepository = AppointmentRepository(apiService)

                val authStore = AuthStore(context = this)
                val loginViewModel = LoginViewModel(appointmentRepository, authStore)

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // Configura la navegación en NavHost
                    NavHost(navController, startDestination = "login", modifier = Modifier.padding(innerPadding)) {
                        // Pantalla de Login

// MainActivity.kt
// MainActivity.kt

// MainActivity.kt

                        composable("login") {
                            // Usa viewModel() para obtener el LoginViewModel dentro del composable
                            val loginViewModel: LoginViewModel = viewModel(factory = LoginViewModelFactory(appointmentRepository, authStore))

                            LoginScreen(
                                vm = loginViewModel,  // Pasa el ViewModel aquí
                                navController = navController, // Pasa el navController
                                onSuccess = {
                                    // Después de un login exitoso, navega a la pantalla de "appointments"
                                    navController.navigate("appointments")
                                }
                            )
                        }



// MainActivity.kt
// MainActivity.kt

// MainActivity.kt
                        composable("appointments") {
                            AppointmentsScreen(
                                navController = navController,  // Solo pasa el navController
                                onOpenDetail = { id -> navController.navigate("appointment_detail/$id") }  // Enlace a los detalles de la cita
                            )
                        }



                        composable("appointment_detail/{id}") { backStackEntry ->
                            // Obtenemos el id desde los argumentos
                            val id = backStackEntry.arguments?.getString("id")?.toLong() ?: 0L

                            // Aquí pasamos el navController y el onBack
                            AppointmentDetailScreen(
                                id = id,
                                navController = navController,  // Pasa el navController
                                onBack = { navController.popBackStack() }  // Pasa el onBack (navegar hacia atrás)
                            )
                        }

                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SIRMAAndroidTheme {
        Greeting("Android")
    }
}