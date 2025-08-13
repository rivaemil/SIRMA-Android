// ui/appointments/AppointmentDetailScreen.kt

package com.example.sirma_android.appointments

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppointmentDetailScreen(
    id: Long,
    navController: NavController,  // Recibe el navController
    onBack: () -> Unit,            // Recibe la función onBack
    vm: AppointmentDetailViewModel = viewModel()  // Obtén el ViewModel con viewModel()
) {
    val state by vm.ui.collectAsState()

    LaunchedEffect(id) { vm.load(id) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Detalle de Cita") })
        }
    ) { padding ->
        Column(Modifier.padding(padding).padding(16.dp)) {
            when {
                state.loading -> CircularProgressIndicator()
                state.error != null -> Text("Error: ${state.error}")
                state.item == null -> Text("No encontrado")
                else -> {
                    val ap = state.item
                    Text(ap!!.title, style = MaterialTheme.typography.headlineSmall)
                    Spacer(Modifier.height(8.dp))
                    Text("Fecha: ${ap.scheduledAt.replace('T',' ').take(16)}")
                    Text("Cliente: ${ap.client?.name ?: "-"}")
                    Text("Vehículo: ${ap.vehicle?.marca ?: "-"} ${ap.vehicle?.modelo ?: ""} ${ap.vehicle?.anio ?: ""}")
                    Spacer(Modifier.height(16.dp))
                    // Botón para volver atrás
                    OutlinedButton(onClick = onBack) { Text("Volver") }
                }
            }
        }
    }
}
