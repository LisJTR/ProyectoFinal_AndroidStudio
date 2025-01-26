package com.torre.proyectofinal.navigation

// Importa la clase NavController que permite gestionar la navegación entre pantallas
import androidx.navigation.NavController
import com.torre.proyectofinal.data.User

// Declaramos un objeto singleton que será el encargado de manejar la navegación
// Se usa un objeto para que no se tenga que crear una instancia cada vez que queramos usarlo.
object AppNavigator {

    // Este método es responsable de navegar a la pantalla "screenBienvenida"
    // Recibe un NavController que es el encargado de manejar la navegación
    fun navigateToBienvenida(navController: NavController) {
        navController.navigate("screenBienvenida")  // Realiza la navegación a la pantalla de bienvenida
    }

    // Este método navega a la pantalla "inicioUserScreen", que probablemente sea la pantalla de inicio del usuario
    fun navigateToinicioUser(navController: NavController) {
        navController.navigate("inicioUserScreen")  // Realiza la navegación a la pantalla de inicio de usuario
    }

    // Este método navega a la pantalla "registroScreen", la cual puede ser una pantalla de confirmación o de registro
    fun navigateToRegistroUser(navController: NavController) {
        navController.navigate("registroScreen")  // Realiza la navegación a la pantalla de éxito/registro
    }

    // Navega a la pantalla de consulta de usuario pasando su nombre y correo electrónico.
    fun navigateToConsultaUser(navController: NavController, user: User) {
        // Navega a la pantalla "consultaUserScreen" pasando el nombre y el correo del usuario en la URL.
        navController.navigate("consultaUserScreen/${user.name}/${user.email}")
    }

    // Aquí se podrían agregar más métodos de navegación si en el futuro se añaden nuevas pantallas.
    // Cada método sería responsable de manejar la navegación hacia una pantalla específica de la app.
}