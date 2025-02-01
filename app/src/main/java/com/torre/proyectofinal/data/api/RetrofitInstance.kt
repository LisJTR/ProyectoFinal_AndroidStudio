package com.torre.proyectofinal.data.api

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Interceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private const val BASE_URL = "https://api.themoviedb.org/3/"

    private val client = OkHttpClient.Builder().build()

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val movieApiService: MovieApiService by lazy {
        retrofit.create(MovieApiService::class.java)
    }

    private const val BASE_URL_PEXELS = "https://api.pexels.com/"

    // Añadimos un interceptor para los encabezados
    private val pexelsClient = OkHttpClient.Builder()
        .addInterceptor(Interceptor { chain ->
            val request: Request = chain.request().newBuilder()
                .addHeader("Authorization", "eCDbyY5ibg7Ol3pINMuHkJHOkEwhcjvI7wwK1G9WVkdqoV77WfZ5BsxP")  // Tu clave de API
                .build()
            chain.proceed(request)
        })
        .build()

    // Configuración de Retrofit para Pexels
    val retrofitInstance: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL_PEXELS)  // Usamos la URL base de Pexels
            .client(pexelsClient)  // Usamos el cliente con el interceptor
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val pexelsApiService: PexelsApiService by lazy {
        retrofitInstance.create(PexelsApiService::class.java)
    }
}
