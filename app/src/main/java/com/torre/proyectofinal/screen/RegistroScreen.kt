package com.torre.proyectofinal.screen

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.torre.proyectofinal.R
import com.google.accompanist.pager.*
import com.torre.proyectofinal.viewmodel.MainViewModel
import kotlinx.coroutines.delay
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RegistroScreen(navController: NavController, userViewModel: MainViewModel,  mainViewModel: MainViewModel) {
    // Variables para almacenar los valores del nombre y correo electrónico ingresados
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    // Estado para controlar la visibilidad del Dialog
    var showDialog by remember { mutableStateOf(false) }

    // Cargar la imagen (asegúrate de que la imagen esté en la carpeta drawable)
    val image: Painter = painterResource(id = com.torre.proyectofinal.R.drawable.usuario_)

    // Obtener la lista de URLs de video desde el ViewModel
    val videoUrls by mainViewModel.videoUrls.observeAsState(emptyList())

    // Llamar a startVideoLoop() en el ViewModel
    LaunchedEffect(Unit) {
        mainViewModel.startVideoLoop()
    }



    // Diseño de la pantalla
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Imagen de fondo
        Image(
            painter = painterResource(id = R.drawable.registro),  // Aquí es donde cargas el recurso de la imagen
            contentDescription = "Fondo de pantalla",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop // Para que la imagen cubra toda la pantalla
        )

        // Contenido de la pantalla (sobre la imagen)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Espaciado desde la parte superior
            Spacer(modifier = Modifier.height(50.dp)) // Espacio de 50dp desde la parte superior

            // Título de la pantalla
            Text(
                text = "Datos para Registrarse",
                fontSize = 32.sp,  // Aumentado el tamaño de la fuente
                color = Color.White,  // Establecer el color de la letra a blanco
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Espacio entre el título y la imagen
            Spacer(modifier = Modifier.height(25.dp))  // Añadido espacio entre el título y la imagen

            // Imagen debajo del título
            Image(
                painter = image,
                contentDescription = "Imagen de registro",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            // Espacio entre el título y la imagen
            Spacer(modifier = Modifier.height(25.dp))  // Añadido espacio entre el título y la imagen

            // Campo de texto para ingresar el nombre
            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text(" \uD83D\uDC64 Nombre", color = Color.White) },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,  // Fondo transparente
                    focusedIndicatorColor = Color.White,  // Color del indicador cuando está enfocado
                    unfocusedIndicatorColor = Color.White  // Color del indicador cuando no está enfocado
                )
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Campo de texto para ingresar el correo electrónico
            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text(" \uD83D\uDCE7\u200B Correo Electrónico", color = Color.White) },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,  // Fondo transparente
                    focusedIndicatorColor = Color.White,  // Color del indicador cuando está enfocado
                    unfocusedIndicatorColor = Color.White  // Color del indicador cuando no está enfocado
                )
            )
            Spacer(modifier = Modifier.height(70.dp))

            // Mostramos los videos
            // Pasamos la lista directamente a InfiniteVideosCarousel
            if (videoUrls.isNotEmpty()) {
                Log.d("RegistroScreen", "Mostrando videos: $videoUrls")
                InfiniteVideosCarousel(videos = videoUrls)  // Aquí pasamos la lista de URLs con todos los videos
            }



            Spacer(modifier = Modifier.height(16.dp))

            // Espaciado flexible para empujar el botón hacia abajo
            Spacer(modifier = Modifier.weight(1f))

            // Botón para registrar al usuario
            Button(
                onClick = {
                    if (name.isNotBlank() && email.isNotBlank()) {
                        // Guardar el usuario en la base de datos usando el ViewModel
                        userViewModel.addUser(name, email)
                        userViewModel.incrementAccessCount(email)  // 🔥 Asegurar que el contador sube
                        // Mostrar el Dialog de confirmación
                        showDialog = true
                    } else {
                        Toast.makeText(
                            navController.context,
                            "Por favor, ingrese todos los datos",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth() // Hace el botón de ancho completo
                    .height(56.dp) // Aumenta la altura del botón
            ) {
                Text("Registrar", fontSize = 18.sp)
            }

            // Espaciado debajo del botón para separarlo de la parte inferior
            Spacer(modifier = Modifier.height(24.dp)) // Añadido espacio debajo del botón
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
                title = { Text(" ✅ Registro exitoso") },
                text = { Text("El usuario ha sido registrado correctamente.") },
                confirmButton = {}  // Sin botón, solo muestra el mensaje y lo quita después de 2 segundos
            )
        }


    }
}

// Carrusel de videos (se muestra uno por uno)
@OptIn(ExperimentalPagerApi::class)
@Composable
fun InfiniteVideosCarousel(videos: List<String>) {
    val pagerState = rememberPagerState()

    // Lógica para cambiar automáticamente de video cada 5 segundos (ajustado para más lento)
    LaunchedEffect(Unit) {
        while (true) {
            delay(10000)  // Espera 5 segundos entre cambios de video
            val currentPage = pagerState.currentPage
            val nextPage = (currentPage + 1) % videos.size  // Cambiar al siguiente video
            pagerState.animateScrollToPage(
                nextPage,
                animationSpec = tween(
                    durationMillis = 990000,
                    easing = LinearEasing
                )
            )  // Deslizar al siguiente video con animación suave
        }
    }

    // Usamos el HorizontalPager para el carrusel de videos
    HorizontalPager(
        count = videos.size,  // La cantidad de videos disponibles
        state = pagerState,
        modifier = Modifier.fillMaxWidth()
    ) { page ->
        VideoPlayer(videoUrl = videos[page])  // Muestra el video correspondiente en la página
    }
}

@Composable
fun VideoPlayer(videoUrl: String) {
    Log.d("VideoPlayer", "Playing video: $videoUrl")

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    // Creamos un YouTube Player
    val playerView = remember { YouTubePlayerView(context) }

    DisposableEffect(playerView) {
        lifecycleOwner.lifecycle.addObserver(playerView)
        playerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                Log.d("VideoPlayer", "YouTubePlayer is ready")
                // Extraemos el videoId de la URL
                val videoId = videoUrl.substringAfter("v=").substringBefore("&")
                Log.d("VideoPlayer", "Video ID: $videoId")
                // Cargamos el video con el ID extraído
                youTubePlayer.loadVideo(videoId, 0f)
                Log.d("VideoPlayer", "Video loaded")
            }
        })

        onDispose {
            playerView.release()
        }
    }

    // Usamos AndroidView para integrarlo en Jetpack Compose
    AndroidView(
        factory = { playerView },
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(16 / 9f) // Mantiene la proporción 16:9
            .height(120.dp)
    )
}