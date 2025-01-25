package com.torre.proyectofinal.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController


@Composable
fun RegistroScreen(navController: NavController) {
    // Pantalla de éxito que se muestra después de añadir un usuario
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("User added successfully!")
        Button(
            onClick = {
                // Navegar de vuelta a la pantalla de formulario (o a otra pantalla)
                navController.popBackStack()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Go back")
        }
    }
}