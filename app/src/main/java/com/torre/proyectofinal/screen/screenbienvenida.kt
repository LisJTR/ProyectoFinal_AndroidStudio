package com.torre.proyectofinal.screen


import android.widget.ImageView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.torre.proyectofinal.R  // Asegúrate de tener esta importación si la imagen está en recursos
import com.torre.proyectofinal.navigation.AppNavigator.navigateToinicioUser
import com.airbnb.lottie.compose.*

@Composable
fun ScreenBienvenida(navController: NavController) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.animationinicio)) // Animación desde res/raw/inicio.json
    val progress by animateLottieCompositionAsState(composition, iterations = LottieConstants.IterateForever)


    // Usamos un Column para apilar la imagen y el texto de forma vertical
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray) // Fondo gris claro
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Cargar y mostrar la imagen
        //val image: Painter = painterResource(id = R.drawable.bienvenida) // Reemplaza 'your_image_name' con el nombre de tu imagen
       // Image(
           // painter = image,
          //  contentDescription = "App Logo",
           // modifier = Modifier.size(600.dp) // Ajusta el tamaño de la imagen, por ejemplo, 200dp de ancho y alto
        //)

        // Animación en lugar de la imagen
        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = Modifier.size(600.dp) // Ajusta el tamaño de la animación
        )

        Spacer(modifier = Modifier.height(16.dp))  // Espacio entre la imagen y el texto de bienvenida

        // Texto de bienvenida con tamaño de fuente más grande
        Text(
            text = "Welcome to the App!",
            fontSize = 32.sp // Ajusta el tamaño aquí, por ejemplo, a 32.sp
        )

        Spacer(modifier = Modifier.height(16.dp))  // Espacio entre el texto y el botón

        // Botón para navegar a la siguiente pantalla
        Button(
            onClick = {  // La acción que se ejecuta cuando el botón es presionado
                // Cuando el botón es presionado, navegamos a la pantalla del formulario
                navigateToinicioUser(navController)  // Llama al método de navegación 'navigateToBienvenida' de AppNavigator
            },
            modifier = Modifier
                .fillMaxWidth()  // Hace el botón tan ancho como la pantalla
                .height(60.dp)  // Ajusta la altura del botón
        ) {
            Text(
                text = "Start",
                fontSize = 20.sp // Tamaño de fuente más grande para el texto del botón
            )
        }
    }
}
