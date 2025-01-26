package com.torre.proyectofinal.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController


@Composable  // Indica que esta función define una pantalla o parte de la interfaz de usuario.
fun RegistroScreen(navController: NavController) {
    // Define el contenido de la pantalla de registro (success screen).

    Column(
        modifier = Modifier
            .fillMaxSize()  // Hace que el `Column` ocupe todo el tamaño disponible en pantalla.
            .padding(16.dp)  // Añade un margen interno de 16 dp alrededor del contenido.
    ) {
        Text("User added successfully!")
        // Muestra un mensaje en texto plano indicando que un usuario fue añadido correctamente.

        Button(
            onClick = {
                // Acción que ocurre al pulsar el botón.
                // Usa `navController.popBackStack()` para regresar a la pantalla anterior.
                navController.popBackStack()
            },
            modifier = Modifier.fillMaxWidth()  // El botón ocupará todo el ancho disponible.
        ) {
            Text("Go back")
            // Texto mostrado dentro del botón.
        }
    }
}