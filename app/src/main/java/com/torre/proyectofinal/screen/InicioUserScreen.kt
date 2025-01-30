package com.torre.proyectofinal.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.torre.proyectofinal.navigation.AppNavigator.navigateToConsultaUser
import com.torre.proyectofinal.navigation.AppNavigator.navigateToRegistroUser
import com.torre.proyectofinal.viewmodel.MainViewModel
import kotlinx.coroutines.launch
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.LaunchedEffect

@Composable
fun InicioUserScreen(userViewModel: MainViewModel, navController: NavController) {
    val name = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val errorMessage = remember { mutableStateOf("") }

    // Observamos la película aleatoria desde el ViewModel
    val movie = userViewModel.movies.observeAsState()

    // Cargamos la película aleatoria cuando se inicia la pantalla
    LaunchedEffect(Unit) {
        userViewModel.getRandomMovie("en-US")  // O el idioma que prefieras
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Título
        Text(
            text = "Bienvenid@",
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        // Campo de texto para el nombre
        TextField(
            value = name.value,
            onValueChange = { name.value = it },
            label = { Text("Nombre de usuario") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Campo de texto para el correo
        TextField(
            value = email.value,
            onValueChange = { email.value = it },
            label = { Text("Correo electrónico") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Mostrar mensaje de error si hay uno
        if (errorMessage.value.isNotEmpty()) {
            Text(
                text = errorMessage.value,
                color = androidx.compose.ui.graphics.Color.Red,
                fontSize = 14.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        // Botón para validar condiciones de registro
        Button(
            onClick = {
                if (name.value.isBlank() || email.value.isBlank()) {
                    errorMessage.value = "Ambos campos son obligatorios."
                } else {
                    errorMessage.value = ""
                    userViewModel.getUserByEmail(email.value) { user ->
                        if (user != null) {
                            navigateToConsultaUser(navController, user)
                        } else {
                            navigateToRegistroUser(navController)
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Inicio de Sesión")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Mostrar los detalles de la película aleatoria
        movie.value?.results?.firstOrNull()?.let { movie ->
            Column(modifier = Modifier.padding(8.dp)) {
                Text(text = "Título: ${movie.title}", fontSize = 20.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Descripción: ${movie.overview ?: "Sin descripción disponible"}", fontSize = 16.sp)
                Spacer(modifier = Modifier.height(8.dp))
                // Si quieres mostrar más detalles, puedes añadir más Texts con propiedades como fecha de lanzamiento, etc.
            }
        }
    }
}

