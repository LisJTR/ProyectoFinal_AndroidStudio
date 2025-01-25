package com.torre.proyectofinal.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.torre.proyectofinal.data.UserDao

// Esta clase es un 'Factory' que se usa para crear instancias del ViewModel
// MainViewModelFactory es una clase que extiende de 'ViewModelProvider.Factory'
// Esto es necesario cuando tu ViewModel tiene dependencias que no se pueden crear automáticamente,
// como un repositorio o un DAO (Data Access Object) como en este caso.
class MainViewModelFactory(
    private val userDao: UserDao,
    private val context: Context
) : ViewModelProvider.Factory {

    // El método 'create' es el encargado de crear el ViewModel.
    // Es necesario para la inicialización del ViewModel con las dependencias (en este caso, 'userDao')
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        // Aquí se verifica si el ViewModel que se desea crear es una instancia de MainViewModel.
        // Si es así, se crea y devuelve el objeto 'MainViewModel' pasándole el 'userDao'.
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(userDao, context) as T  // Se retorna el 'MainViewModel' como tipo T.
        }

        // Si el 'modelClass' no es de tipo 'MainViewModel', lanzamos una excepción
        // porque no se puede crear ese tipo de ViewModel.
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
