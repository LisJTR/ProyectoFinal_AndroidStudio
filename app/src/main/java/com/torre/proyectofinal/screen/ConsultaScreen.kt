package com.torre.proyectofinal.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.torre.proyectofinal.data.User
import com.torre.proyectofinal.viewmodel.MainViewModel

@Composable
fun ConsultaScreen(navController: NavController, userEmail: String, mainViewModel: MainViewModel) {
    var user by remember { mutableStateOf<User?>(null) }
    var showNotification by remember { mutableStateOf(true) }  // Estado para mostrar la notificación

    // Obtener los datos del usuario por su correo electrónico
    LaunchedEffect(userEmail) {
        mainViewModel.getUserByEmail(userEmail) { retrievedUser ->
            if (retrievedUser != null) {
                user = retrievedUser
                // Incrementar el contador de accesos al cargar la pantalla
                mainViewModel.incrementAccessCount(userEmail)
            } else {
                Log.e("ConsultaScreen", "Usuario no encontrado con email: $userEmail")
            }
        }
    }

    user?.let { retrievedUser ->
        Box(modifier = Modifier.fillMaxSize()) { // Usamos Box para manejar la superposición de la notificación

            // Mostrar notificación en el centro de la pantalla
            if (showNotification) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center) // Centra la notificación
                        .background(androidx.compose.ui.graphics.Color.Gray.copy(alpha = 0.7f)) // Gris claro con opacidad para el fondo externo
                ) {
                    // Contenedor de la notificación con el fondo gris
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.8f) // Ancho de la notificación, ocupando 80% del ancho de la pantalla
                            .fillMaxHeight(0.4f) // Alto de la notificación, ocupando 40% de la pantalla
                            .background(
                                androidx.compose.ui.graphics.Color.Gray,
                                shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp)
                            ) // Fondo gris para la notificación
                            .padding(24.dp), // Más padding para que sea más espaciosa
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(), // Asegura que la columna ocupe todo el espacio disponible
                            verticalArrangement = Arrangement.Center, // Centra el contenido verticalmente
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            // Datos del usuario
                            Text(
                                text = "Nombre: ${retrievedUser.name}",
                                fontSize = 18.sp, // Aumentamos el tamaño de texto
                                color = androidx.compose.ui.graphics.Color.Black
                            )
                            Text(
                                text = "Correo: ${retrievedUser.email}",
                                fontSize = 18.sp, // Aumentamos el tamaño de texto
                                color = androidx.compose.ui.graphics.Color.Black
                            )
                            Text(
                                text = "Fecha: ${retrievedUser.registrationDate}",
                                fontSize = 18.sp, // Aumentamos el tamaño de texto
                                color = androidx.compose.ui.graphics.Color.Black
                            )
                            Text(
                                text = "Accesos: ${retrievedUser.accessCount}",
                                fontSize = 18.sp, // Aumentamos el tamaño de texto
                                color = androidx.compose.ui.graphics.Color.Black
                            )

                            Spacer(modifier = Modifier.height(16.dp)) // Espacio entre los datos y el botón

                            // Botón debajo de los datos
                            Button(
                                onClick = { showNotification = false }, // Cerrar la notificación
                                modifier = Modifier.fillMaxWidth() // Ocupa el 100% del ancho
                            ) {
                                Text("Cerrar")
                            }
                        }
                    }
                }
            }

            // El contenido de la pantalla de consulta (debajo de la notificación)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // Parte superior derecha: fecha y contador
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    Column(
                        horizontalAlignment = Alignment.End
                    ) {
                        // Fecha
                        Text(
                            text = "Fecha: ${retrievedUser.registrationDate}",
                            fontSize = 14.sp
                        )
                        // Contador de accesos
                        Text(
                            text = "Accesos: ${retrievedUser.accessCount}",
                            fontSize = 14.sp
                        )
                    }
                }

                // Título
                Text("Consulta de Usuario", fontSize = 24.sp)

                Spacer(modifier = Modifier.height(16.dp))

                // Información adicional
                Text("Nombre: ${retrievedUser.name}")
                Text("Correo: ${retrievedUser.email}")

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        navController.popBackStack()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Regresar")
                }
            }
        }
    } ?: run {
        // Si el usuario no existe
        Text("Usuario no encontrado", fontSize = 20.sp)
    }
}
