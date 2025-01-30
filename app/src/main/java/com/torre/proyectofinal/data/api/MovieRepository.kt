package com.torre.proyectofinal.data.api

class MovieRepository(private val movieApiService: MovieApiService) {

    suspend fun getPopularMovies(apiKey: String, language: String): MovieResponse {
        return movieApiService.getPopularMovies(apiKey, language)
    }
}
