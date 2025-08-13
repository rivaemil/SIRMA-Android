// ui/appointments/AppointmentsScreen.kt
package com.example.sirma_android.appointments

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun AppointmentsScreen(
    navController: NavController,
    onOpenDetail: (Long) -> Unit
) {
    // Obtén el ViewModel dentro del composable
    val vm: AppointmentsViewModel = viewModel()  // Aquí se obtiene el ViewModel usando viewModel()

    val state by vm.ui.collectAsState()

    val today = LocalDate.now()
    var start by remember { mutableStateOf(today.withDayOfMonth(1)) }
    var end by remember { mutableStateOf(today.withDayOfMonth(today.lengthOfMonth())) }

    LaunchedEffect(start, end) {
        vm.load(start.toString(), end.toString())
    }

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Mis Citas", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(16.dp))
        Row(Modifier.padding(top = 8.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedButton(onClick = {
                val prev = start.minusMonths(1)
                start = prev.withDayOfMonth(1)
                end = prev.withDayOfMonth(prev.lengthOfMonth())
            }) { Text("Mes anterior") }

            OutlinedButton(onClick = {
                val next = start.plusMonths(1)
                start = next.withDayOfMonth(1)
                end = next.withDayOfMonth(next.lengthOfMonth())
            }) { Text("Mes siguiente") }
        }
        Spacer(Modifier.height(12.dp))

        when {
            state.loading -> CircularProgressIndicator()
            state.error != null -> Text("Error: ${state.error}")
            state.items.isEmpty() -> Text("No tienes citas en el rango seleccionado.")
            else -> {
                val fmtDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(state.items) { ap ->
                        ElevatedCard(
                            Modifier.fillMaxWidth().clickable { onOpenDetail(ap.id) }
                        ) {
                            Column(Modifier.padding(12.dp)) {
                                Text(ap.title, style = MaterialTheme.typography.titleMedium)
                                Text(
                                    text = ap.scheduledAt.replace('T', ' ').take(16), // simple view
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Text(
                                    text = "Vehículo: ${ap.vehicle?.marca ?: "-"} ${ap.vehicle?.modelo ?: ""}",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
