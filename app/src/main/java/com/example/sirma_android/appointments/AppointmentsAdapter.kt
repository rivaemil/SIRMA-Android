package com.example.sirma_android.appointments


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sirma_android.R

class AppointmentsAdapter(private val appointments: List<Appointment>) : RecyclerView.Adapter<AppointmentsAdapter.AppointmentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_appointment, parent, false)
        return AppointmentViewHolder(view)
    }

    override fun onBindViewHolder(holder: AppointmentViewHolder, position: Int) {
        val appointment = appointments[position]
        holder.bind(appointment)
    }

    override fun getItemCount(): Int {
        return appointments.size
    }

    class AppointmentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.appointment_title)
        private val dateTextView: TextView = itemView.findViewById(R.id.appointment_date)
        private val clientNameTextView: TextView = itemView.findViewById(R.id.client_name)
        private val vehicleInfoTextView: TextView = itemView.findViewById(R.id.vehicle_info)

        fun bind(appointment: Appointment) {
            titleTextView.text = appointment.title
            dateTextView.text = appointment.scheduled_at
            clientNameTextView.text = "Cliente: ${appointment.client.name}"
            vehicleInfoTextView.text = "Vehículo: ${appointment.vehicle.brand} ${appointment.vehicle.model} (${appointment.vehicle.year})"
        }
    }
}
