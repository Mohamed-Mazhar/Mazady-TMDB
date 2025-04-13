package com.example.mazadytmdb.features.movies.domain.model

data class Movie(
    val id: Int,
    val title: String,
    val posterUrl: String,
    val releaseDate: String,
    val isFavorite: Boolean = false
)