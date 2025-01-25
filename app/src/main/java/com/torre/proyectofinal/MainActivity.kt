package com.torre.proyectofinal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {

    // Este método es llamado cuando la actividad es creada.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Se obtiene la instancia de 'userDao' de la base de datos.
            // Aquí, AppDatabase.getDatabase(applicationContext).userDao() te proporciona el acceso a 'userDao'.
            val userDao = AppDatabase.getDatabase(applicationContext).userDao()

            // Se crea una instancia de MainViewModelFactory con el 'userDao' para pasar las dependencias al ViewModel.
            val factory = MainViewModelFactory(userDao)

            // Se crea una instancia del ViewModel usando el ViewModelProvider con la fábrica.
            // Esto asegura que el ViewModel es creado con las dependencias necesarias (en este caso, 'userDao').
            val userViewModel = androidx.lifecycle.ViewModelProvider(this, factory).get(MainViewModel::class.java)

            // Se muestra la interfaz de usuario con el ViewModel que hemos creado.
            UserFormScreen(userViewModel = userViewModel)
        }
    }
}

@Composable
fun UserFormScreen(userViewModel: MainViewModel) {
    // Se crean los estados para el nombre y el correo, que se usan para controlar los valores en los campos de entrada.
    val name = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }

    // Se define la UI con un formulario para ingresar un nuevo usuario.
    Column(modifier = Modifier.padding(16.dp)) {
        // Campo de texto para ingresar el nombre del usuario.
        TextField(
            value = name.value,
            onValueChange = { name.value = it },
            label = { Text("Enter Name") },
            modifier = Modifier.fillMaxWidth()  // El campo se ajusta al ancho disponible.
        )
        Spacer(modifier = Modifier.height(8.dp))  // Espacio entre los campos.

        // Campo de texto para ingresar el correo del usuario.
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
            },
            modifier = Modifier.fillMaxWidth()  // El botón se ajusta al ancho disponible.
        ) {
            Text("Add User")  // Texto dentro del botón.
        }

        Spacer(modifier = Modifier.height(16.dp))  // Espacio entre el botón y la lista de usuarios.

        // Mostrar la lista de usuarios usando 'observeAsState' para observar cambios en 'userList' del ViewModel.
        val users = userViewModel.userList.observeAsState(emptyList())

        // Aseguramos que 'userList' no es null y mostramos la lista de usuarios.
        users.value?.let { userList ->
            // Para cada usuario en la lista, mostramos su nombre y correo.
            userList.forEach { user ->
                Text(text = "Name: ${user.name}, Email: ${user.email}")
            }
        }
    }
}
