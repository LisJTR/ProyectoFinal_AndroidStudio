package com.torre.proyectofinal.screen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.torre.proyectofinal.R
import com.torre.proyectofinal.data.api.PexelsImage
import com.torre.proyectofinal.viewmodel.MainViewModel
import kotlinx.coroutines.delay


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


    // Cargar imágenes de la API de Pexels
    val pexelsImages by mainViewModel.pexelsImages.collectAsState()

    // Llamada a la API para obtener imágenes aleatorias
    LaunchedEffect(Unit) {
        mainViewModel.getRandomImages("nature")  // Aquí llamamos a la función para obtener las imágenes aleatorias
    }
   // Depuración: Verificar si las imágenes están llegando correctamente
   // LaunchedEffect(pexelsImages) {
   //     println("Imágenes obtenidas: ${pexelsImages?.photos}")
   // }

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
            Spacer(modifier = Modifier.height(16.dp))

            // Llamamos al InfiniteImageCarousel para crear el hilo de imágenes
            if (pexelsImages != null && pexelsImages?.photos?.isNotEmpty() == true) {
                // Aquí pasamos las imágenes obtenidas de la API de Pexels al "hilo" de imágenes
                InfiniteImageCarousel(photo = pexelsImages?.photos ?: emptyList())
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
    @Composable
    fun InfiniteImageCarousel(photo: List<PexelsImage>) {
        val infiniteImages = remember { mutableStateListOf<PexelsImage>().apply { addAll(photo) } }


        LaunchedEffect(Unit) {
            while (true) {
                delay(3000) // Esperar 3 segundos
                if (infiniteImages.isNotEmpty()) {
                    infiniteImages.add(infiniteImages.first()) // Agregar la primera imagen al final
                    infiniteImages.removeAt(0)  // Eliminar la primera imagen
                }
            }
        }

        // Mostrar las imágenes de Pexels
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(infiniteImages) { image ->  // ✅ Usamos `infiniteImages`
                AsyncImage(
                    model = image.src.medium,  // ✅ Correcto acceso a la imagen
                    contentDescription = "Imagen de Pexels",
                    modifier = Modifier.fillMaxHeight(),
                    contentScale = ContentScale.Crop
                )
            }
        }

}

