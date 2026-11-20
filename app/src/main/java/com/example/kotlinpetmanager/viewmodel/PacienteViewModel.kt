package com.example.kotlinpetmanager.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.kotlinpetmanager.model.Paciente

class PacienteViewModel : ViewModel() {

    // Lista observable: la pantalla se redibuja sola cuando cambia.
    // "private set" = solo el ViewModel puede modificarla.
    var pacientes by mutableStateOf(listOf<Paciente>())
        private set

    // Campos del formulario (también viven en el ViewModel)
    var nombre by mutableStateOf("")
        private set
    var especie by mutableStateOf("")
        private set
    var edad by mutableStateOf("")
        private set
    var contacto by mutableStateOf("")
        private set
    var esUrgencia by mutableStateOf(false)
        private set
    var mensajeError by mutableStateOf<String?>(null)
        private set

    private var siguienteId = 1

    fun onNombreChange(valor: String) { nombre = valor }
    fun onEspecieChange(valor: String) { especie = valor }
    fun onContactoChange(valor: String) { contacto = valor }
    fun onUrgenciaChange(valor: Boolean) { esUrgencia = valor }

    // Solo deja escribir números (máximo 2 dígitos)
    fun onEdadChange(valor: String) {
        if (valor.length <= 2 && valor.all { it.isDigit() }) {
            edad = valor
        }
    }

    fun agregarPaciente() {
        val edadNumero = edad.toIntOrNull()

        // Validación
        if (nombre.isBlank() || especie.isBlank() || contacto.isBlank() || edadNumero == null) {
            mensajeError = "Por favor completa todos los campos."
            return
        }

        val nuevo = Paciente(
            id = siguienteId++,
            nombre = nombre.trim(),
            especie = especie.trim(),
            edad = edadNumero,
            esUrgencia = esUrgencia,
            contacto = contacto.trim()
        )

        pacientes = pacientes + nuevo   // crea una lista nueva con el paciente añadido
        limpiarFormulario()
    }

    fun limpiarFormulario() {
        nombre = ""
        especie = ""
        edad = ""
        contacto = ""
        esUrgencia = false
        mensajeError = null
    }
}