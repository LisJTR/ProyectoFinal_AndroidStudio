package com.torre.proyectofinal.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.torre.proyectofinal.data.User
import com.torre.proyectofinal.data.UserDao
import com.torre.proyectofinal.data.api.MovieRepository
import com.torre.proyectofinal.data.api.MovieResponse
import kotlinx.coroutines.launch

class MainViewModel(
    private val userDao: UserDao,
    private val context: Context,
    private val movieRepository: MovieRepository,
    private val apiKey: String
) : ViewModel() {

    private val _userList = MutableLiveData<List<User>>(emptyList())
    val userList: LiveData<List<User>> get() = _userList

    private val _movies = MutableLiveData<MovieResponse?>(null)
    val movies: LiveData<MovieResponse?> get() = _movies

    private var newDate: String? = null
    private var oldDate: String? = null

    fun addUser(name: String, email: String) {
        val registrationDate = getCurrentDate()
        val user = User(name = name, email = email, registrationDate = registrationDate)
        viewModelScope.launch {
            userDao.insert(user)
            loadUsers()
        }
    }

    fun loadUsers() {
        viewModelScope.launch {
            _userList.postValue(userDao.getAllUsers())
        }
    }

    fun getUserByEmail(email: String, onResult: (User?) -> Unit) {
        viewModelScope.launch {
            val user = userDao.getUserByEmail(email)
            onResult(user)
            if (user != null) {
                oldDate = user.registrationDate
            }
        }
    }

    fun incrementAccessCount(email: String) {
        viewModelScope.launch {
            userDao.incrementAccessCount(email)
        }
    }

    fun getCurrentDate(): String {
        val currentDate = java.text.SimpleDateFormat("dd/MM/yyyy", java.util.Locale.getDefault()).format(java.util.Date())
        return currentDate
    }

    fun getNewDate(): String? {
        newDate = getCurrentDate()
        return newDate
    }

    fun updateOldDate(userEmail: String) {
        oldDate = newDate
        newDate = null
        updateUserRegistrationDate(userEmail, oldDate ?: "")
    }



    fun getOldDate(): String? {
        return oldDate
    }

    // Obtener películas populares desde el repositorio
    fun getRandomMovie(language: String) {
        viewModelScope.launch {
            try {
                val response = movieRepository.getPopularMovies(apiKey, language)
                val randomMovie = response.results?.random() // Obtener una película aleatoria
                _movies.postValue(MovieResponse(results = listOf(randomMovie))) // Pasamos solo esa película
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error obteniendo película aleatoria: ${e.message}")
            }
        }
    }

    // En MainViewModel
    fun updateUserRegistrationDate(userEmail: String, newRegistrationDate: String) {
        viewModelScope.launch {
            val user = userDao.getUserByEmail(userEmail)
            user?.let {
                // Actualizamos la fecha en la base de datos
                val updatedUser = it.copy(registrationDate = newRegistrationDate)
                userDao.update(updatedUser)  // Método que deberías agregar en UserDao
            }
        }
    }


}

