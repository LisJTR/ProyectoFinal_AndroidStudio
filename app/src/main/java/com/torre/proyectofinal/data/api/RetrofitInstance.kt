package com.torre.proyectofinal.data.api

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Interceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitInstance {

    private const val BASE_URL_PEXELS = "https://api.pexels.com/"
    private const val BASE_URL_MOVIE = "https://api.themoviedb.org/3/"
    private const val PEXELS_API_KEY = "eCDbyY5ibg7Ol3pINMuHkJHOkEwhcjvI7wwK1G9WVkdqoV77WfZ5BsxP" // Tu clave de API

    // Interceptor para añadir headers a las peticiones de Pexels
    private val pexelsInterceptor = Interceptor { chain ->
        val request = chain.request()
        val newRequest = if (request.url.toString().contains(BASE_URL_PEXELS)) {
            // Si la petición es a Pexels, añadimos la clave de API
            val modifiedRequest = request.newBuilder()
           // request.newBuilder()
                .addHeader("Authorization", PEXELS_API_KEY)
                .addHeader("User-Agent", "MiAplicacionAndroid/1.0 ") // Reemplaza con tu información
                .addHeader("Connection", "keep-alive ")
                .addHeader("Accept", "application/json")
                .build()
            Log.d("Retrofit", "User-Agent enviado: ${modifiedRequest.header("User-Agent")}${modifiedRequest.header("Connection")}${modifiedRequest.header("Accept")}")
            modifiedRequest

        } else {
            // Si no es a Pexels, no añadimos nada
            request
        }
        chain.proceed(newRequest)
    }

    // OkHttpClient con el interceptor
    private val client = OkHttpClient.Builder()
        .addInterceptor(pexelsInterceptor)
        .build()

    // Instancia de Retrofit para Pexels
    val retrofitInstance: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL_PEXELS)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    // Instancia de Retrofit para Movie
    val retrofitInstanceMovie: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL_MOVIE)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Instancia de PexelsApiService
    val pexelsApiService: PexelsApiService by lazy {
        retrofitInstance.create(PexelsApiService::class.java)
    }
    // Instancia de MovieApiService
    val movieApiService: MovieApiService by lazy {
        retrofitInstanceMovie.create(MovieApiService::class.java)
    }
}