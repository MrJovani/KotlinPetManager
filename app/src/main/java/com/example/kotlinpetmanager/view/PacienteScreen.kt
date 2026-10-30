package com.example.kotlinpetmanager.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.kotlinpetmanager.model.Paciente
import com.example.kotlinpetmanager.viewmodel.PacienteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PacienteScreen(viewModel: PacienteViewModel) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("KotlinPet Manager 🐾") })
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            // ---------- FORMULARIO ----------
            OutlinedTextField(
                value = viewModel.nombre,
                onValueChange = viewModel::onNombreChange,
                label = { Text("Nombre") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = viewModel.especie,
                onValueChange = viewModel::onEspecieChange,
                label = { Text("Especie / Raza") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = viewModel.edad,
                onValueChange = viewModel::onEdadChange,
                label = { Text("Edad (años)") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = viewModel.contacto,
                onValueChange = viewModel::onContactoChange,
                label = { Text("Contacto de emergencia") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("¿Es urgencia?")
                Spacer(modifier = Modifier.width(8.dp))
                Switch(
                    checked = viewModel.esUrgencia,
                    onCheckedChange = viewModel::onUrgenciaChange
                )
            }

            if (viewModel.error.isNotEmpty()) {
                Text(
                    text = viewModel.error,
                    color = MaterialTheme.colorScheme.error
                )
            }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = { viewModel.agregarPaciente() }) {
                    Text("Registrar Paciente")
                }
                OutlinedButton(onClick = { viewModel.limpiarFormulario() }) {
                    Text("Limpiar")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ---------- LISTA ----------
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(viewModel.pacientes) { paciente ->
                    PacienteCard(paciente)
                }
            }
        }
    }
}

@Composable
fun PacienteCard(paciente: Paciente) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.elevatedCardColors(
            containerColor = if (paciente.esUrgencia)
                MaterialTheme.colorScheme.errorContainer
            else
                MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = paciente.nombre,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text("Especie/Raza: ${paciente.especie}")
            Text("Edad: ${paciente.edad} años")
            Text("Contacto: ${paciente.contacto}")
            if (paciente.esUrgencia) {
                Text("🚨 URGENCIA", fontWeight = FontWeight.Bold)
            }
        }
    }
}