package com.torre.proyectofinal.data.api

data class PexelsResponse(
    val photos: List<PexelsImage>
)

data class PexelsImage(
    val id: Int,
    val width: Int,
    val height: Int,
    val src: ImageSrc
)

data class ImageSrc(
    val original: String,
    val large: String,
    val medium: String,
    val small: String
)


