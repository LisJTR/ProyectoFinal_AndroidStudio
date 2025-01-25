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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.*

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Obtener la instancia de 'userDao' de la base de datos
            val userDao = AppDatabase.getDatabase(applicationContext).userDao()

            // Crear el factory para el ViewModel
            val factory = MainViewModelFactory(userDao, applicationContext)

            // Crear una instancia del ViewModel utilizando el ViewModelFactory
            val userViewModel = androidx.lifecycle.ViewModelProvider(this, factory).get(MainViewModel::class.java)

            // Obtener el NavController para gestionar la navegación
            val navController = rememberNavController()

            // Usar el NavHost para gestionar la navegación
            NavHost(navController = navController, startDestination = "userFormScreen") {
                composable("userFormScreen") {
                    // Aquí colocamos la pantalla del formulario de usuario
                    UserFormScreen(userViewModel = userViewModel, navController = navController)
                }
                // Pantalla de éxito
                composable("successScreen") {
                    SuccessScreen(navController = navController)
                }
            }
        }
    }
}

@Composable
fun UserFormScreen(userViewModel: MainViewModel, navController: NavController) {
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

@Composable
fun SuccessScreen(navController: NavController) {
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
