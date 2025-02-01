package com.torre.proyectofinal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.torre.proyectofinal.data.AppDatabase
import com.torre.proyectofinal.data.api.MovieApiService
import com.torre.proyectofinal.data.api.MovieRepository
import com.torre.proyectofinal.data.api.PexelsApiService
import com.torre.proyectofinal.data.api.PexelsRepository
import com.torre.proyectofinal.data.api.RetrofitInstance
import com.torre.proyectofinal.viewmodel.MainViewModel
import com.torre.proyectofinal.viewmodel.MainViewModelFactory
import com.torre.proyectofinal.screen.ConsultaScreen
import com.torre.proyectofinal.screen.FinApp
import com.torre.proyectofinal.screen.InicioUserScreen
import com.torre.proyectofinal.screen.RegistroScreen
import com.torre.proyectofinal.screen.ScreenBienvenida

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            // Crear instancia del MovieRepository
            val movieApiService = MovieApiService.create()  // Usar el método 'create' del companion object
            val movieRepository = MovieRepository(movieApiService)

            // Usar la instancia global de RetrofitInstance para PexelsApiService
            val pexelsRepository = PexelsRepository(RetrofitInstance.pexelsApiService)
            // Usar la instancia global de RetrofitInstance

            // Obtener la instancia de 'userDao' de la base de datos
            val userDao = AppDatabase.getDatabase(applicationContext).userDao()

            // Definir las claves API
            val movieApiKey = "7bbe53fb4f5d755bcad2e1a3f6ed72e7"  //  API key para películas
            val pexelsApiKey = "eCDbyY5ibg7Ol3pINMuHkJHOkEwhcjvI7wwK1G9WVkdqoV77WfZ5BsxP"  //  API key para Pexels

            // Crear el factory para el ViewModel
            val factory = MainViewModelFactory(userDao, applicationContext, movieRepository,
                pexelsRepository, movieApiKey, pexelsApiKey)

            // Crear una instancia del ViewModel utilizando el ViewModelFactory
            val userViewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)

            // Obtener el NavController para gestionar la navegación
            val navController = rememberNavController()

            // Usar el NavHost para gestionar la navegación
            NavHost(navController = navController, startDestination = "screenBienvenida") {
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
                    RegistroScreen(navController = navController, userViewModel = userViewModel, mainViewModel = userViewModel)
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
                // Aquí registramos la pantalla FinApp
                composable("finApp") {
                    FinApp(navController = navController, mainViewModel = userViewModel )  // Asegúrate de pasar el NavController
                }
            }
        }
    }
}

