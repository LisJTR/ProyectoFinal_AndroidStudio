package com.torre.proyectofinal.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {

    @GET("popular")
    suspend fun getPopularMovies(@Query("api_key") apiKey: String, @Query("language") language: String): MovieResponse

    companion object {
        private const val BASE_URL = "https://api.themoviedb.org/3/movie/"

        fun create(): MovieApiService {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(MovieApiService::class.java)
        }
    }
}


