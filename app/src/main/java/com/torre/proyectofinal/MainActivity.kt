package com.torre.proyectofinal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.torre.proyectofinal.screen.ScreenBienvenida
import com.torre.proyectofinal.screen.InicioUserScreen
import com.torre.proyectofinal.screen.RegistroScreen
import com.torre.proyectofinal.viewmodel.MainViewModel
import com.torre.proyectofinal.viewmodel.MainViewModelFactory
import com.torre.proyectofinal.data.AppDatabase
import com.torre.proyectofinal.screen.ConsultaScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Obtener la instancia de 'userDao' de la base de datos
            val userDao = AppDatabase.getDatabase(applicationContext).userDao()

            // Crear el factory para el ViewModel
            val factory = MainViewModelFactory(userDao, applicationContext)

            // Crear una instancia del ViewModel utilizando el ViewModelFactory
            val userViewModel = androidx.lifecycle.ViewModelProvider(this, factory).get(MainViewModel::class.java)

            // Obtener el NavController para gestionar la navegación
            val navController = rememberNavController()

            // Usar el NavHost para gestionar la navegación
            androidx.navigation.compose.NavHost(navController = navController, startDestination = "screenBienvenida") {
                composable("screenBienvenida") {
                    // Aquí colocamos la pantalla de bienvenida
                    ScreenBienvenida(navController = navController)
                }
                composable("inicioUserScreen") {
                    // Pantalla de formulario de usuario
                    InicioUserScreen(userViewModel = userViewModel, navController = navController)
                }
                composable("registroScreen") {
                    // Pantalla de éxito
                    RegistroScreen(navController = navController, userViewModel = userViewModel)
                }
                composable(
                    "consultaUserScreen/{userEmail}",
                    arguments = listOf(
                        navArgument("userEmail") { type = NavType.StringType }
                    )
                ) { backStackEntry ->
                    // Recuperar los argumentos
                    val userEmail = backStackEntry.arguments?.getString("userEmail") ?: ""

                    // Pasar los valores a la pantalla de consulta
                    ConsultaScreen(navController = navController, userEmail = userEmail, mainViewModel = userViewModel)
                }
            }
        }
    }
}
