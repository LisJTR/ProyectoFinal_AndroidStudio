package com.torre.proyectofinal.data.api



class PexelsRepository(private val pexelsApiService: PexelsApiService) {

    suspend fun getImages(page: Int, perPage: Int, query: String, orientation: String): PexelsResponse {
        return pexelsApiService.getImages(page, perPage, query, orientation) // Elimina el tipo Call
    }
}



