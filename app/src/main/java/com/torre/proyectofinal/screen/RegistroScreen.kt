package com.torre.proyectofinal.screen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.torre.proyectofinal.viewmodel.MainViewModel
import kotlinx.coroutines.delay

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RegistroScreen(navController: NavController, userViewModel: MainViewModel) {
    // Variables para almacenar los valores del nombre y correo electrónico ingresados
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    // Estado para controlar la visibilidad del Dialog
    var showDialog by remember { mutableStateOf(false) }

    // Cargar la imagen (asegúrate de que la imagen esté en la carpeta drawable)
    val image: Painter = painterResource(id = com.torre.proyectofinal.R.drawable.imagenregistro)

    // Diseño de la pantalla
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Título de la pantalla
            Text(
                text = "Datos para Registrarse",
                fontSize = 24.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Imagen debajo del título
            Image(
                painter = image,
                contentDescription = "Imagen de registro",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            // Campo de texto para ingresar el nombre
            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Campo de texto para ingresar el correo electrónico
            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Correo Electrónico") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Botón para registrar al usuario
            Button(
                onClick = {
                    if (name.isNotBlank() && email.isNotBlank()) {
                        // Guardar el usuario en la base de datos usando el ViewModel
                        userViewModel.addUser(name, email)
                        // Mostrar el Dialog de confirmación
                        showDialog = true
                    } else {
                        Toast.makeText(navController.context, "Por favor, ingrese todos los datos", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Registrar")
            }
        }

        // Mostrar el Dialog en el centro de la pantalla después de registrar
        if (showDialog) {
            // LaunchedEffect para hacer que el Dialog desaparezca después de un retraso
            LaunchedEffect(Unit) {
                delay(2000)  // Esperar 2 segundos (puedes ajustar este valor)
                showDialog = false  // Cerrar el Dialog
                navController.popBackStack()  // Regresar a la pantalla de inicio
            }

            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Registro exitoso") },
                text = { Text("El usuario ha sido registrado correctamente.") },
                confirmButton = {}  // Sin botón, solo muestra el mensaje y lo quita después de 2 segundos
            )
        }
    }
}
