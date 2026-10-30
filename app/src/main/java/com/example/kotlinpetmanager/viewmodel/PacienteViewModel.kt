package com.example.kotlinpetmanager.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.kotlinpetmanager.model.Paciente

class PacienteViewModel : ViewModel() {

    // Lista de pacientes: la vista puede leerla, pero solo el ViewModel puede cambiarla
    var pacientes by mutableStateOf(listOf<Paciente>())
        private set

    // Campos del formulario
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

    // Mensaje de error (vacío = sin error)
    var error by mutableStateOf("")
        private set

    private var siguienteId = 1

    // Funciones que la pantalla llama cuando el usuario escribe
    fun onNombreChange(valor: String) { nombre = valor }
    fun onEspecieChange(valor: String) { especie = valor }
    fun onEdadChange(valor: String) { edad = valor.filter { it.isDigit() } } // solo números
    fun onContactoChange(valor: String) { contacto = valor }
    fun onUrgenciaChange(valor: Boolean) { esUrgencia = valor }

    fun agregarPaciente() {
        val edadNumero = edad.toIntOrNull()

        // Validación: ningún campo vacío y la edad debe ser un número
        if (nombre.isBlank() || especie.isBlank() || contacto.isBlank() || edadNumero == null) {
            error = "Completa todos los campos"
            return
        }

        val nuevo = Paciente(
            id = siguienteId,
            nombre = nombre.trim(),
            especie = especie.trim(),
            edad = edadNumero,
            contacto = contacto.trim(),
            esUrgencia = esUrgencia
        )
        siguienteId++
        pacientes = pacientes + nuevo   // crea una lista nueva con el paciente al final
        limpiarFormulario()
    }

    fun limpiarFormulario() {
        nombre = ""
        especie = ""
        edad = ""
        contacto = ""
        esUrgencia = false
        error = ""
    }
}