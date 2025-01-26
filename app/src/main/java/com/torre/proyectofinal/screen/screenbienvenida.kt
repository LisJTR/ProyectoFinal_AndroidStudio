package com.torre.proyectofinal.screen


import android.widget.ImageView
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.torre.proyectofinal.R  // Asegúrate de tener esta importación si la imagen está en recursos
import com.torre.proyectofinal.navigation.AppNavigator.navigateToinicioUser


@Composable
fun ScreenBienvenida(navController: NavController) {
    // Usamos un Column para apilar la imagen y el texto de forma vertical
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Cargar y mostrar la imagen
        val image: Painter = painterResource(id = R.drawable.bienvenida) // Reemplaza 'your_image_name' con el nombre de tu imagen
        Image(painter = image, contentDescription = "App Logo")

        Spacer(modifier = Modifier.height(16.dp))  // Espacio entre la imagen y el texto de bienvenida

        // Texto de bienvenida
        Text(text = "Welcome to the App!")

        Spacer(modifier = Modifier.height(16.dp))  // Espacio entre el texto y el botón

        // Botón para navegar a la siguiente pantalla
        Button(
            onClick = {  // La acción que se ejecuta cuando el botón es presionado
                // Cuando el botón es presionado, navegamos a la pantalla del formulario
                navigateToinicioUser(navController)  // Llama al método de navegación 'navigateToBienvenida' de AppNavigator
            }
        ) {
            Text("Start")
        }
    }
}
