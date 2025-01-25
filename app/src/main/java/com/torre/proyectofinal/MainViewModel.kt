package com.torre.proyectofinal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel(private val userDao: UserDao) : ViewModel() {

    // LiveData para mantener y observar la lista de usuarios
    private val _userList = MutableLiveData<List<User>>(emptyList())
    val userList: LiveData<List<User>> get() = _userList

    // Método para insertar un nuevo usuario
    fun addUser(name: String, email: String) {
        val user = User(name = name, email = email)
        viewModelScope.launch {
            userDao.insert(user)  // Insertar el usuario en la base de datos
            _userList.postValue(userDao.getAllUsers())  // Actualizamos la lista de usuarios
        }
    }

    // Método para obtener todos los usuarios
    fun getAllUsers() {
        viewModelScope.launch {
            _userList.postValue(userDao.getAllUsers())  // Obtener todos los usuarios y actualizar LiveData
        }
    }
}
