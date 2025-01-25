package com.torre.proyectofinal

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch





class MainViewModel(private val userDao: UserDao, private val context: Context) : ViewModel() {

    // LiveData para mantener y observar la lista de usuarios
    private val _userList = MutableLiveData<List<User>>(emptyList())
    val userList: LiveData<List<User>> get() = _userList

    // Método para insertar un nuevo usuario
    fun addUser(name: String, email: String) {
        val user = User(name = name, email = email)
        viewModelScope.launch {
            userDao.insert(user)  // Insertar el usuario en la base de datos
            loadUsers()  // Recargar la lista de usuarios después de la inserción
            logDatabasePath()  // Agregar log para mostrar la ruta de la base de datos
        }
    }

    // Método para obtener todos los usuarios
    fun loadUsers() {
        viewModelScope.launch {
            _userList.postValue(userDao.getAllUsers())  // Obtener todos los usuarios y actualizar LiveData
        }
    }

    // Método para obtener la ruta de la base de datos
    private fun logDatabasePath() {
        // Aquí obtenemos la ruta de la base de datos
        val databasePath = context.getDatabasePath("user_database").absolutePath
        Log.d("MainViewModel", "Database Path: $databasePath")
    }
}
