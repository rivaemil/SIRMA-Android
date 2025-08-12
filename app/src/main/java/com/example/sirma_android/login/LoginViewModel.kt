package com.example.sirma_android.login


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sirma_android.data.AppointmentRepository
import com.example.sirma_android.data.AuthStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface LoginState {
    object Idle : LoginState
    object Loading : LoginState
    data class Error(val msg: String) : LoginState
    object Success : LoginState
}

class LoginViewModel(
    private val repo: AppointmentRepository,
    private val store: AuthStore
) : ViewModel() {

    private val _state = MutableStateFlow<LoginState>(LoginState.Idle)
    val state = _state.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _state.value = LoginState.Loading
            try {
                val res = repo.login(email, password) // API Call
                store.saveToken(res.token) // Guardar token
                _state.value = LoginState.Success
            } catch (e: Exception) {
                _state.value = LoginState.Error(e.message ?: "Error")
            }
        }
    }
}
