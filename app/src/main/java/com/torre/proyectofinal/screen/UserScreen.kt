package com.torre.proyectofinal.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.torre.proyectofinal.viewmodel.MainViewModel

@Composable
fun UserScreen(userViewModel: MainViewModel, navController: NavController) {
    // Se crean los estados para el nombre y el correo
    val name = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }

    // Se define la UI con un formulario para ingresar un nuevo usuario
    Column(modifier = Modifier.padding(16.dp)) {
        // Campo de texto para ingresar el nombre del usuario
        TextField(
            value = name.value,
            onValueChange = { name.value = it },
            label = { Text("Enter Name") },
            modifier = Modifier.fillMaxWidth()  // El campo se ajusta al ancho disponible.
        )
        Spacer(modifier = Modifier.height(8.dp))  // Espacio entre los campos.

        // Campo de texto para ingresar el correo del usuario
        TextField(
            value = email.value,
            onValueChange = { email.value = it },
            label = { Text("Enter Email") },
            modifier = Modifier.fillMaxWidth()  // El campo se ajusta al ancho disponible.
        )
        Spacer(modifier = Modifier.height(16.dp))  // Espacio después de los campos.

        // Botón que, al ser presionado, ejecutará la función 'addUser' en el ViewModel,
        // pasando el nombre y correo como parámetros.
        Button(
            onClick = {
                userViewModel.addUser(name.value, email.value)
                // Después de añadir el usuario, navegar a una pantalla de éxito
                navController.navigate("successScreen")
            },
            modifier = Modifier.fillMaxWidth()  // El botón se ajusta al ancho disponible.
        ) {
            Text("Add User")  // Texto dentro del botón
        }

        Spacer(modifier = Modifier.height(16.dp))  // Espacio entre el botón y la lista de usuarios

        // Mostrar la lista de usuarios usando 'observeAsState' para observar cambios en 'userList' del ViewModel
        val users = userViewModel.userList.observeAsState(emptyList())

        // Aseguramos que 'userList' no es null y mostramos la lista de usuarios
        users.value?.let { userList ->
            userList.forEach { user ->
                Text(text = "Name: ${user.name}, Email: ${user.email}")
            }
        }
    }
}
