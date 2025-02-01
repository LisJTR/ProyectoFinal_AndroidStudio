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
import com.torre.proyectofinal.data.api.PexelsRepository
import com.torre.proyectofinal.data.api.PexelsResponse
import com.torre.proyectofinal.data.api.RetrofitInstance
import com.torre.proyectofinal.data.api.RetrofitInstance.pexelsApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val userDao: UserDao,
    private val context: Context,
    private val movieRepository: MovieRepository,
    private val pexelsRepository: PexelsRepository,
    private val apiKey: String,
    private val pexelsApiKey: String  // Agregar la clave API de Pexels como parámetro
) : ViewModel() {

    private val _userList = MutableLiveData<List<User>>(emptyList())
    val userList: LiveData<List<User>> get() = _userList

    private val _movies = MutableLiveData<MovieResponse?>(null)
    val movies: LiveData<MovieResponse?> get() = _movies

    // 🔥 Nuevo MutableLiveData para actualizar el contador en tiempo real
    private val _userAccessCount = MutableLiveData<Int>()
    val userAccessCount: LiveData<Int> get() = _userAccessCount

    // LiveData para las imágenes de Pexels
    private val _pexelsImages = MutableStateFlow<PexelsResponse?>(null)
    val pexelsImages: StateFlow<PexelsResponse?> = _pexelsImages.asStateFlow()



    //variables para manejar las fechas
    private var newDate: String? = null
    private var oldDate: String? = null

    // Variables para manejar los contadores
    private var currentAccessCount: Int = 0  // Este será el contador de la sesión actual (nuevo)
    private var previousAccessCount: Int = 0  // Este es el contador antiguo, debe mantenerse entre sesiones



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
        }
    }


    // Incrementar el contador de accesos al iniciar sesión
    fun incrementAccessCount(email: String) {
        viewModelScope.launch {
            val user = userDao.getUserByEmail(email)
            user?.let {
                userDao.updateAccessCounts(email) // Actualiza los contadores en la BD
                _userAccessCount.postValue(it.accessCount + 1) //  Actualiza LiveData
            }
        }
    }


    // Actualizar el contador cuando el usuario cierre la aplicación
    fun updateAccessCountOnExit(email: String) {
        viewModelScope.launch {
            val user = userDao.getUserByEmail(email)
            user?.let {
                // Actualizar en la base de datos el nuevo contador
                it.accessCount = previousAccessCount
                userDao.updateAccessCount(email, previousAccessCount)
            }
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



    // Actualizar la fecha en la base de datos
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

    // Nuevo método para obtener imágenes desde la API de Pexels
    fun getImagesFromPexels(query: String) {
        viewModelScope.launch {
            try {
                // Obteniendo las imágenes desde la API
                val response = pexelsApiService.getImages(page = 1, perPage = 10, query = query)
                _pexelsImages.value = response // Asignamos la respuesta al estado
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error obteniendo imágenes de Pexels: ${e.message}")
            }
        }
    }

    // Función para obtener imágenes aleatorias desde la API de Pexels
    fun getRandomImages(query: String) {
        viewModelScope.launch {
            try {
                // Obteniendo imágenes aleatorias desde la API de Pexels
                val response = pexelsApiService.getImages(page = 1, perPage = 10,  query = query )  // Sin query, para obtener imágenes aleatorias
                _pexelsImages.value = response  // Asignamos la respuesta al estado
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error obteniendo imágenes de Pexels: ${e.message}")
            }
        }
    }
}