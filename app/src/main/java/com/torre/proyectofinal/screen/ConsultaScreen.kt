package com.torre.proyectofinal.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun ConsultaScreen(navController: NavController, userName: String, userEmail: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()  // Hace que el Column ocupe todo el tamaño disponible en pantalla.
            .padding(16.dp)  // Añade un margen interno de 16 dp alrededor del contenido.
    ) {
        Text("Consulta de Usuario")

        Spacer(modifier = Modifier.height(16.dp))

        // Mostrar los detalles del usuario
        Text("Nombre: $userName")
        Text("Correo: $userEmail")

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para regresar
        Button(
            onClick = {
                // Regresar a la pantalla anterior
                navController.popBackStack()
            },
            modifier = Modifier.fillMaxWidth()  // El botón ocupa todo el ancho disponible
        ) {
            Text("Regresar")
        }
    }
}
