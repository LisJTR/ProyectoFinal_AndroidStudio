
package com.torre.proyectofinal.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.torre.proyectofinal.R
import com.torre.proyectofinal.navigation.AppNavigator.navigateToConsultaUser
import com.torre.proyectofinal.navigation.AppNavigator.navigateToRegistroUser
import com.torre.proyectofinal.viewmodel.MainViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InicioUserScreen(userViewModel: MainViewModel, navController: NavController) {
    val name = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val errorMessage = remember { mutableStateOf("") }

    //val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.animation_)) // Animación desde res/raw/inicio.json
    //val progress by animateLottieCompositionAsState(
     //   composition,
      //  iterations = LottieConstants.IterateForever
    //)


    // Observamos la película aleatoria desde el ViewModel
    //val movie = userViewModel.movies.observeAsState()

    // Cargamos la película aleatoria cuando se inicia la pantalla
    LaunchedEffect(Unit) {
        userViewModel.getRandomMovie("en-US")  // O el idioma que prefieras
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
           // .background(Color(0xFF616E61)) // Verde lavanda claro

    ) {
        // 🔹 Animación de fondo
       // LottieAnimation(
         //   composition = composition,
         //   progress = { progress },
          //  modifier = Modifier
          //      .fillMaxSize() // Hace que la animación cubra toda la pantalla

       // )

        // Imagen de fondo
        Image(
            painter = painterResource(id = R.drawable.inicio_),  // Aquí es donde cargas el recurso de la imagen
            contentDescription = "Fondo de pantalla",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop // Para que la imagen cubra toda la pantalla
        )

        // 🔹 Contenido de la pantalla encima del fondo
        Column(
            modifier = Modifier
                .fillMaxSize()
                //.fillMaxWidth() // Utilizamos solo el ancho máximo
                .padding(30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Bienvenid@",
                fontSize = 36.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                color = Color.White, // Texto blanco
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 40.dp)
            )

            TextField(
                value = name.value,
                onValueChange = { name.value = it },
                label = { Text(" \uD83D\uDC64 Nombre de usuario", color = Color.White) },
                modifier = Modifier.fillMaxWidth(),
                colors = androidx.compose.material3.TextFieldDefaults.textFieldColors(
                    containerColor = Color.White.copy(alpha = 0.3f), // Fondo semitransparente
                    focusedIndicatorColor = Color.White, // Línea blanca cuando está seleccionado
                    unfocusedIndicatorColor = Color.Gray // Línea gris cuando no está seleccionado
                )
            )
            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = email.value,
                onValueChange = { email.value = it },
                label = { Text(" ✉\uFE0F Correo electrónico", color = Color.White) },
                modifier = Modifier.fillMaxWidth(),
                colors = androidx.compose.material3.TextFieldDefaults.textFieldColors(
                    containerColor = Color.White.copy(alpha = 0.3f), // Fondo semitransparente
                    focusedIndicatorColor = Color.White, // Línea blanca cuando está seleccionado
                    unfocusedIndicatorColor = Color.Gray // Línea gris cuando no está seleccionado
                )
            )

            Spacer(modifier = Modifier.height(32.dp)) // Espaciado antes del botón


            if (errorMessage.value.isNotEmpty()) {
                Text(
                    text = errorMessage.value,
                    color = androidx.compose.ui.graphics.Color.Red,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            Button(
                onClick = {
                    if (name.value.isBlank() || email.value.isBlank()) {
                        errorMessage.value = "Ambos campos son obligatorios."
                    } else {
                        errorMessage.value = ""
                        userViewModel.getUserByEmail(email.value) { user ->
                            if (user != null) {
                                // Si el contador es 0 (primer acceso), incrementamos el contador
                                if (user.accessCount == 0) {
                                    userViewModel.incrementAccessCount(email.value)
                                }

                                // Navegar a la pantalla de consulta del usuario
                                navigateToConsultaUser(navController, user)
                            } else {
                                // Si el usuario no existe, navegar a la pantalla de registro
                                navigateToRegistroUser(navController)
                            }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp) // Aumentamos la altura del botón
            ) {
                Text("Inicio de Sesión",
                    fontSize = 18.sp, // Puedes aumentar el tamaño del texto si lo prefieres
                    fontWeight = FontWeight.Bold //  Hacemos el texto más grueso
                )
            }

            //Spacer(modifier = Modifier.height(18.dp))

            // movie.value?.results?.firstOrNull()?.let { movie ->
            //  Column(modifier = Modifier.padding(8.dp)) {
            //     Text(text = "Título: ${movie.title}", fontSize = 20.sp)
            //     Spacer(modifier = Modifier.height(8.dp))
            //     Text(text = "Descripción: ${movie.overview ?: "Sin descripción disponible"}", fontSize = 16.sp)
            //     Spacer(modifier = Modifier.height(8.dp))
            // }
            // }
        }
    }
}

