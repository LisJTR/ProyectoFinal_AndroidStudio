package com.torre.proyectofinal.data.api

import retrofit2.http.GET
import retrofit2.http.Query

interface PexelsApiService {

    @GET("v1/curated")  // O el endpoint que desees usar de Pexels
    suspend fun getImages(  // Cambiar a suspend
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Query("query") query: String,
        @Query("orientation") orientation: String = "landscape",
       // @Query("api_key") pexelsApiKey: String
    ): PexelsResponse  // Cambiar a PexelsResponse directamente
}


