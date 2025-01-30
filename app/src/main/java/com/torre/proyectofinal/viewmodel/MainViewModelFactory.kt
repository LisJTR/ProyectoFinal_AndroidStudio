package com.torre.proyectofinal.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.torre.proyectofinal.data.UserDao
import com.torre.proyectofinal.data.api.MovieRepository

class MainViewModelFactory(
    private val userDao: UserDao,
    private val context: Context,
    private val movieRepository: MovieRepository,
    private val apiKey: String
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(userDao, context, movieRepository, apiKey) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
