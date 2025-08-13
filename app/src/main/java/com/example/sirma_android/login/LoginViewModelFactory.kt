package com.example.sirma_android.login


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sirma_android.data.AppointmentRepository
import com.example.sirma_android.data.AuthStore

class LoginViewModelFactory(
    private val appointmentRepository: AppointmentRepository,
    private val authStore: AuthStore
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(appointmentRepository, authStore) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}
