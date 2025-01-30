package com.torre.proyectofinal.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.torre.proyectofinal.navigation.AppNavigator
import kotlin.system.exitProcess

@Composable
fun FinApp(navController: NavController) {
    // Colocamos el contenido en la pantalla FinApp
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Botón para cerrar la aplicación
        Button(
            onClick = {
                // Finaliza la aplicación
                exitProcess(0)  // Esto termina la aplicación
            },
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        ) {
            Text("Cerrar")
        }

        // Botón para volver a la pantalla Consulta
        Button(
            onClick = {
                // Volver a la pantalla Consulta
                navController.popBackStack()  // Regresar a la pantalla anterior
            },
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        ) {
            Text("Volver atrás")
        }
    }
}
