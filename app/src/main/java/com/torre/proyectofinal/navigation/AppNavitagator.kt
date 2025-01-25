package com.torre.proyectofinal.navigation

import androidx.navigation.NavController

object AppNavigator {
    fun navigateToBienvenida(navController: NavController) {
        navController.navigate("screenBienvenida")
    }

    fun navigateToUserForm(navController: NavController) {
        navController.navigate("userFormScreen")
    }

    fun navigateToSuccess(navController: NavController) {
        navController.navigate("successScreen")
    }

    // Puedes agregar más métodos de navegación aquí a medida que agregues más pantallas
}
