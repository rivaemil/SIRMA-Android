package com.example.sirma_android

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sirma_android.appointments.AppointmentsActivity
import com.example.sirma_android.ui.theme.SIRMAAndroidTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("ViewModelConstructorInComposable")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SIRMAAndroidTheme {
                Column {
                    TopAppBar(title = { Text("Bienvenido") })
                    Button(onClick = { navigateToAppointments() }) {
                        Text("Ir a mis citas")
                    }
            }
        }
    }
}


    // Definir la función de navegación
    private fun navigateToAppointments() {
        val intent = Intent(this, AppointmentsActivity::class.java)
        startActivity(intent)
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SIRMAAndroidTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            TopAppBar(title = { Text("Bienvenido") })
            Button(onClick = { /* Aquí iría la acción de navegación */ }) {
                Text("Ir a mis citas")
            }
        }
    }
}