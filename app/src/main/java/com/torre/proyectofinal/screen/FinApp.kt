package com.torre.proyectofinal.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.torre.proyectofinal.viewmodel.MainViewModel
import kotlin.system.exitProcess

@Composable
fun FinApp(navController: NavController, mainViewModel: MainViewModel) {
    // Observar las imágenes desde el ViewModel usando collectAsState()
    val pexelsImages by mainViewModel.pexels.collectAsState()

    // Llamar a la función que obtiene las imágenes cuando se carga la pantalla
    LaunchedEffect(Unit) {
        mainViewModel.getImagesFromPexels("nature")
    }

    // Colocamos el contenido en la pantalla FinApp
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFF7B99A2) // Fondo suave para la pantalla
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp), // Agregar padding general
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Título o mensaje
            Text(
                text = "Gracias por usar la aplicación",
                fontWeight = FontWeight.Bold,
                fontSize = 35.sp,
                color = Color.White,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // Botón para cerrar la aplicación
            Button(
                onClick = {
                    // Finaliza la aplicación
                    exitProcess(0)  // Esto termina la aplicación
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)  // Separación entre los botones
                    .height(60.dp)  // Altura ajustada del botón
                    .shadow(10.dp, shape = RoundedCornerShape(12.dp)),  // Añadimos sombra
                shape = RoundedCornerShape(12.dp), // Bordes redondeados
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent) // Sin color de fondo directo
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            brush = Brush.linearGradient(
                                listOf(
                                    Color(0xFFA88173), // Color naranja
                                    Color(0xFF917F64) // Color amarillo
                                )
                            ),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .fillMaxSize() // Hace que el gradiente llene el botón
                ) {
                    Text(
                        text = "Cerrar",
                        fontSize = 18.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.Center) // Alineación centrada del texto
                    )
                }
            }

            // Botón para volver a la pantalla Consulta
            Button(
                onClick = {
                    // Volver a la pantalla Consulta
                    navController.popBackStack()  // Regresar a la pantalla anterior
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp)  // Separación entre los botones
                    .height(60.dp)  // Altura ajustada del botón
                    .shadow(17.dp, shape = RoundedCornerShape(15.dp)),  // Añadimos sombra
                shape = RoundedCornerShape(12.dp), // Bordes redondeados
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent) // Sin color de fondo directo
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            brush = Brush.linearGradient(
                                listOf(
                                    Color(0xFF7CA17E), // Color verde
                                    Color(0xFF3B563D)  // Verde más oscuro
                                )
                            ),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .fillMaxSize() // Hace que el gradiente llene el botón
                ) {
                    Text(
                        text = "Volver atrás",
                        fontSize = 18.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.Center) // Alineación centrada del texto
                    )
                }
            }

            // Aquí colocamos el LazyRow fuera de los botones
            Box(modifier = Modifier.fillMaxSize()) { // Contenedor principal para todos los elementos


                // Mostrar las imágenes de Pexels
                pexelsImages?.let { response ->
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)  // Altura ajustada para el carrusel
                            .align(Alignment.BottomCenter) // Asegura que las imágenes estén al fondo
                            .padding(horizontal = 16.dp), // Agregar padding para no pegarse a los bordes
                        horizontalArrangement = Arrangement.spacedBy(8.dp) // Espacio entre imágenes
                    ) {
                        items(response.photos) { photo ->
                            AsyncImage(
                                model = photo.src.large, // Accediendo correctamente a la imagen
                                contentDescription = "Imagen de Pexels",
                                modifier = Modifier
                                    .width(150.dp) // Ajusta el tamaño de las imágenes
                                    .fillMaxHeight()
                                    .clip(RoundedCornerShape(8.dp)), // Añadir bordes redondeados si lo deseas
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }

                // Aquí iría el resto de tu contenido si lo necesitas, por ejemplo, notificaciones u otros elementos
            }

        }
    }
}
