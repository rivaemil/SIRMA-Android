package com.example.sirma_android.appointments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sirma_android.data.AppointmentRepository
import com.example.sirma_android.data.AppointmentDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class AppointmentDetailUiState(
    val loading: Boolean = false,
    val item: AppointmentDto? = null,
    val error: String? = null
)

class AppointmentDetailViewModel(private val repo: AppointmentRepository): ViewModel() {

    private val _ui = MutableStateFlow(AppointmentDetailUiState())
    val ui = _ui.asStateFlow()

    fun load(id: Long) {
        viewModelScope.launch {
            _ui.value = AppointmentDetailUiState(loading = true)
            try {
                _ui.value = AppointmentDetailUiState(item = repo.appointmentDetail(id))
            } catch (e: Exception) {
                _ui.value = AppointmentDetailUiState(error = e.message)
            }
        }
    }
}
