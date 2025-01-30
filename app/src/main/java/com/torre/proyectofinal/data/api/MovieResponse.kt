package com.torre.proyectofinal.data.api

data class MovieResponse(
    val results: List<Movie?>
)

data class Movie(
    val title: String,
    val overview: String,
    val release_date: String,
    val poster_path: String
)
