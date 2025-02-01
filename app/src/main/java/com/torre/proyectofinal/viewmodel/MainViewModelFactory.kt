package com.torre.proyectofinal.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.torre.proyectofinal.data.UserDao
import com.torre.proyectofinal.data.api.MovieRepository
import com.torre.proyectofinal.data.api.PexelsRepository

class MainViewModelFactory(
    private val userDao: UserDao,
    private val context: Context,
    private val movieRepository: MovieRepository,
    private val pexelsRepository: PexelsRepository,
    private val apiKey: String,
    private val pexelsApiKey: String // Clave de API para Pexels
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(userDao, context, movieRepository, pexelsRepository, apiKey, pexelsApiKey) as T // Aquí se pasa
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}