package com.torre.proyectofinal.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.torre.proyectofinal.data.User
import com.torre.proyectofinal.data.UserDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainViewModel(private val userDao: UserDao, private val context: Context) : ViewModel() {

    private val _userList = MutableLiveData<List<User>>(emptyList())
    val userList: LiveData<List<User>> get() = _userList

    fun addUser(name: String, email: String) {
        val registrationDate = getCurrentDate() // Obtener la fecha de registro
        val user = User(name = name, email = email, registrationDate = registrationDate)
        viewModelScope.launch {
            userDao.insert(user) // Insertar el usuario en la base de datos
            loadUsers() // Recargar la lista de usuarios
        }
    }

    fun loadUsers() {
        viewModelScope.launch {
            _userList.postValue(userDao.getAllUsers())
        }
    }

    // Método para obtener un usuario por correo
    fun getUserByEmail(email: String, onResult: (User?) -> Unit) {
        viewModelScope.launch {
            val user = userDao.getUserByEmail(email)
            onResult(user)
        }
    }

    // Incrementar el contador de accesos
    fun incrementAccessCount(email: String) {
        viewModelScope.launch {
            userDao.incrementAccessCount(email) // Incrementa el contador en la base de datos
        }
    }

    private fun getCurrentDate(): String {
        val currentDate = java.text.SimpleDateFormat("dd/MM/yyyy", java.util.Locale.getDefault()).format(java.util.Date())
        return currentDate
    }
}