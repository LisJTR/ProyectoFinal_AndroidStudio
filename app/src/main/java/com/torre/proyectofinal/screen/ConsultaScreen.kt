package com.torre.proyectofinal.screen

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.torre.proyectofinal.data.User
import com.torre.proyectofinal.data.api.Movie
import com.torre.proyectofinal.navigation.AppNavigator.navigateToFinApp
import com.torre.proyectofinal.viewmodel.MainViewModel
import java.util.*
import coil.compose.AsyncImage
import com.torre.proyectofinal.R


@Composable
fun ConsultaScreen(navController: NavController, userEmail: String, mainViewModel: MainViewModel) {
    var user by remember { mutableStateOf<User?>(null) }
    var showNotification by remember { mutableStateOf(true) }
    var showApiReminder by remember { mutableStateOf(true) }
    var currentNewDate by remember { mutableStateOf<String?>(null) }

    var movies by remember { mutableStateOf<List<Movie>?>(null) }
    // Estado para controlar si se muestra la información de la película
    var showMovieDetails by remember { mutableStateOf(false) }


    // Variable para el contador anterior
    var previousAccessCount by remember { mutableStateOf(0) }

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

    // Observamos los cambios en el contador de accesos en tiempo real
    val accessCount by mainViewModel.userAccessCount.observeAsState(initial = 0)

    // Obtener datos del usuario
    LaunchedEffect(userEmail) {
        mainViewModel.getUserByEmail(userEmail) { retrievedUser ->
            if (retrievedUser != null) {
                user = retrievedUser
                currentNewDate = mainViewModel.getNewDate()

                mainViewModel.incrementAccessCount(userEmail) // ✅ Incrementa contador al entrar
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Gray)
        ) {
            // Mostrar la notificación de la API
            if (showApiReminder) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()  // Asegura que el Box ocupe todo el ancho
                        .align(Alignment.BottomCenter)  // Alineamos el Box en la parte inferior
                        .background(Color(0xFFAD715D).copy(alpha = 0.8f))
                        .padding(16.dp)
                        .padding(bottom = 10.dp), // Aquí agregamos un padding adicional en la parte inferior
                    contentAlignment = Alignment.Center

                ) {
                    Text(
                        "🔔 Recuerda que puedes consultar la API",
                        fontSize = 20.sp,
                        color = Color.Black
                    )
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
                            //.background(Color.White, shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp))
                            // .padding(24.dp)
                            .align(Alignment.Center) // Esto asegura que el Box esté centrado en la pantalla

                    ) {
                        // 🔹 Imagen de fondo
                        Image(
                            painter = painterResource(id = R.drawable.nota_),
                            contentDescription = "Fondo de notificación",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize() // 🔹 Ajusta la imagen al tamaño del Box
                        )

                        //Contenido del usuario sobre la imagen
                        Column(
                            modifier = Modifier.fillMaxSize()
                                .fillMaxSize() // 🔹 Asegura que el contenido también llene el Box
                                .padding(24.dp), // 🔹 Agrega padding solo al contenido, NO a la imagen
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                " \uD83D\uDC64 Nombre: ${retrievedUser.name}",
                                fontSize = 18.sp,
                                color = Color.Black
                            )
                            Text(
                                "  \uD83D\uDCE7 Correo: ${retrievedUser.email}",
                                fontSize = 18.sp,
                                color = Color.Black
                            )
                            Text(
                                " \uD83D\uDCC5  Fecha: ${mainViewModel.getOldDate() ?: retrievedUser.registrationDate}",
                                fontSize = 18.sp,
                                color = Color.Black
                            )
                            Text(
                                " \uD83D\uDEC2 Accesos anterior: ${retrievedUser.previousAccessCount}",
                                fontSize = 18.sp,
                                color = Color.Black
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            Button(
                                onClick = {
                                    mainViewModel.updateOldDate(userEmail)
                                    showNotification = false
                                },
                                modifier = Modifier
                                    .fillMaxWidth(0.6f) // 🔹 Reduce el ancho del botón al 60% del disponible
                                    .height(40.dp), // 🔹 Mantén la altura que deseas para el botón
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFFA8D5BA)
                                )
                            ) {
                                Text("Cerrar", fontSize = 20.sp, color = Color.White)
                            }
                        }
                    }
                }
            }


            // Contenido principal de la pantalla
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 18.dp),
                verticalArrangement = Arrangement.Top, // Centra el contenido verticalmente
                horizontalAlignment = Alignment.CenterHorizontally // Centra el contenido horizontalmente
            ) {
                Row(
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                        .background(Color(0xFF939CC7)),
                    horizontalArrangement = Arrangement.End
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.End
                    ) {


                        Text(
                            "\uD83D\uDCC5  Fecha: $currentNewDate",
                            fontSize = 18.sp
                        )
                        // Mostrar el contador actual
                        Text(
                            "\uD83D\uDEC2 Acceso actual: ${retrievedUser.accessCount}",
                            fontSize = 18.sp
                        )
                    }
                }

                // 🔹 Imagen entre la fecha/acceso y el botón de consulta
                Image(
                    painter = painterResource(id = R.drawable.iconocime_), // 🔹 Cambia 'mi_imagen' por tu imagen en res/drawable
                    contentDescription = "Imagen decorativa",
                    modifier = Modifier
                        .fillMaxWidth(0.6f) // 🔹 Ajusta el tamaño de la imagen
                        .height(150.dp) // 🔹 Cambia la altura según necesites
                        .padding(vertical = 8.dp) // 🔹 Agrega espacio arriba y abajo
                )

                // Agregar un Spacer con la altura deseada para el espacio debajo del botón
                Spacer(modifier = Modifier.height(25.dp)) // Ajusta el valor de altura según lo necesites


                // Botón para consultar las películas
                Button(
                    onClick = {
                        showApiReminder = false
                        handler.removeCallbacks(apiReminderRunnable)

                        // Pasamos el idioma del sistema como ejemplo
                        val language = Locale.getDefault().language
                        mainViewModel.getRandomMovie(language)  // Llamamos a la API para obtener las películas
                        showMovieDetails =
                            true  // Mostrar la información de la película al presionar el botón
                    },
                    modifier = Modifier
                        //.fillMaxWidth()
                        .height(50.dp) // Hacer el botón más grande
                ) {
                    Text(" Consulta de Usuario", fontSize = 24.sp)
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Mostrar las películas obtenidas de la API SOLO si showMovieDetails es verdadero
                if (showMovieDetails) {
                    movies?.let { movieList ->
                        LazyColumn(
                            modifier = Modifier
                                //.fillMaxSize()
                                .weight(1f) // Hacer que el LazyColumn ocupe el espacio restante
                                .padding(40.dp)
                        ) {
                            items(movieList) { movie -> // Usar items en LazyColumn
                                // Mostrar la imagen de la película
                                AsyncImage(
                                    model = "https://image.tmdb.org/t/p/w500${movie.poster_path}", // URL completa de la imagen
                                    contentDescription = "Poster de la película ${movie.title}",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                )

                                // Agregar un espacio entre la imagen y la descripción
                                Spacer(modifier = Modifier.height(15.dp)) // Espacio de 8dp


                                Text("Título: ${movie.title}")
                                Text("Descripción: ${movie.overview}")
                                Spacer(
                                    modifier = Modifier
                                        .fillMaxHeight(0.8f) // Esto limita la altura total de la columna a un 80% de la pantalla

                                )
                            }
                        }
                    }
                }


                // Botón para ir a la pantalla fin
                Button(
                    onClick = {
                        navigateToFinApp(navController)
                    },
                    modifier = Modifier
                        // .fillMaxWidth()
                        .height(60.dp) // Hacer el botón más grande
                        .align(Alignment.CenterHorizontally) // Asegura que el botón esté centrado

                ) {
                    Text("Dejar de consultar", fontSize = 24.sp)
                }
                // Agregar un Spacer con la altura deseada para el espacio debajo del botón
                Spacer(modifier = Modifier.height(25.dp)) // Ajusta el valor de altura según lo necesites
            }
        }
    } ?: run {
        Text("Usuario no encontrado", fontSize = 20.sp)
    }

}

