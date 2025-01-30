package com.torre.proyectofinal.screen

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.torre.proyectofinal.data.User
import com.torre.proyectofinal.data.api.Movie
import com.torre.proyectofinal.navigation.AppNavigator.navigateToFinApp
import com.torre.proyectofinal.viewmodel.MainViewModel
import java.util.*

@Composable
fun ConsultaScreen(navController: NavController, userEmail: String, mainViewModel: MainViewModel) {
    var user by remember { mutableStateOf<User?>(null) }
    var showNotification by remember { mutableStateOf(true) }
    var showApiReminder by remember { mutableStateOf(true) }
    var currentNewDate by remember { mutableStateOf<String?>(null) }

    var movies by remember { mutableStateOf<List<Movie>?>(null) }
    var showMovieDetails by remember { mutableStateOf(false) }  // Agregar esta variable

    // Handler para el hilo de la notificación
    val handler = remember { Handler(Looper.getMainLooper()) }
    val apiReminderRunnable = remember {
        object : Runnable {
            override fun run() {
                showApiReminder = !showApiReminder
                handler.postDelayed(this, 500)
            }
        }
    }

    // Obtener datos del usuario
    LaunchedEffect(userEmail) {
        mainViewModel.getUserByEmail(userEmail) { retrievedUser ->
            if (retrievedUser != null) {
                user = retrievedUser
                mainViewModel.incrementAccessCount(userEmail)
                currentNewDate = mainViewModel.getNewDate()
            } else {
                Log.e("ConsultaScreen", "Usuario no encontrado con email: $userEmail")
            }
        }
    }

    // Usar observeAsState() para observar el LiveData de las películas
    val movieState = mainViewModel.movies.observeAsState(initial = null)
    movies = movieState.value?.results as List<Movie>?

    // Iniciar el hilo de la notificación
    LaunchedEffect(Unit) {
        handler.post(apiReminderRunnable)
    }

    user?.let { retrievedUser ->

        Box(modifier = Modifier.fillMaxSize()) {
            // Mostrar la notificación de la API
            if (showApiReminder) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.BottomCenter)
                        .background(Color.Yellow.copy(alpha = 0.8f))
                        .padding(16.dp)
                ) {
                    Text("🔔 Recuerda que puedes consultar la API", fontSize = 16.sp, color = Color.Black)
                }
            }

            // Mostrar la notificación del usuario
            if (showNotification) {
                Box(
                    modifier = Modifier
                        .fillMaxSize() // Cubre toda la pantalla
                        .background(Color.Black.copy(alpha = 0.7f)) // Capa semitransparente
                        .pointerInput(Unit) {
                            detectTapGestures { } // Bloquea cualquier toque
                        }
                        .zIndex(1f) // Asegura que esta capa esté por encima de todos los demás elementos
                ) {
                    // Caja blanca con los datos del usuario
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .fillMaxHeight(0.4f)
                            .background(Color.White, shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp))
                            .padding(24.dp)
                            .align(Alignment.Center) // Esto asegura que el Box esté centrado en la pantalla
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("Nombre: ${retrievedUser.name}", fontSize = 18.sp, color = Color.Black)
                            Text("Correo: ${retrievedUser.email}", fontSize = 18.sp, color = Color.Black)
                            Text("Fecha: ${mainViewModel.getOldDate() ?: retrievedUser.registrationDate}", fontSize = 18.sp, color = Color.Black)
                            Text("Accesos: ${retrievedUser.accessCount}", fontSize = 18.sp, color = Color.Black)

                            Spacer(modifier = Modifier.height(16.dp))

                            Button(
                                onClick = {
                                    mainViewModel.updateOldDate(userEmail)
                                    showNotification = false
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Cerrar")
                            }
                        }
                    }
                }
            }

            // Contenido principal de la pantalla
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    Column(horizontalAlignment = Alignment.End) {
                        Text("Fecha: $currentNewDate", fontSize = 14.sp)
                        Text("Accesos: ${retrievedUser.accessCount}", fontSize = 14.sp)
                    }
                }

                // Botón para consultar las películas
                Button(
                    onClick = {
                        showApiReminder = false
                        handler.removeCallbacks(apiReminderRunnable)

                        // Pasamos el idioma del sistema como ejemplo
                        val language = Locale.getDefault().language
                        mainViewModel.getRandomMovie(language)  // Llamamos a la API para obtener las películas
                        showMovieDetails = true  // Mostrar la información de la película al presionar el botón
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Consulta de Usuario", fontSize = 24.sp)
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Mostrar los datos del usuario
                Text("Nombre: ${retrievedUser.name}")
                Text("Correo: ${retrievedUser.email}")

                Spacer(modifier = Modifier.height(16.dp))

                // Mostrar las películas obtenidas de la API SOLO si showMovieDetails es verdadero
                if (showMovieDetails) {
                    movies?.let { movieList ->
                        movieList.forEach { movie ->
                            Text("Título: ${movie.title}")
                            Text("Descripción: ${movie.overview}")
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Botón para regresar
                Button(
                    onClick = {
                        navigateToFinApp(navController)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Regresar")
                }
            }
        }
    } ?: run {
        Text("Usuario no encontrado", fontSize = 20.sp)
    }
}
