package com.example.sirma_android.appointments

// ui/appointments/AppointmentsViewModel.kt

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sirma_android.data.AppointmentRepository
import com.example.sirma_android.data.AppointmentDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class AppointmentsUiState(
    val loading: Boolean = false,
    val items: List<AppointmentDto> = emptyList(),
    val error: String? = null
)

class AppointmentsViewModel(private val repo: AppointmentRepository) : ViewModel() {

    private val _ui = MutableStateFlow(AppointmentsUiState())
    val ui = _ui.asStateFlow()

    fun load(desde: String?, hasta: String?) {
        viewModelScope.launch {
            _ui.value = _ui.value.copy(loading = true, error = null)
            try {
                val list = repo.myAppointments(desde, hasta) // API Call
                _ui.value = AppointmentsUiState(items = list)
            } catch (e: Exception) {
                _ui.value = AppointmentsUiState(error = e.message ?: "Error")
            }
        }
    }
}
