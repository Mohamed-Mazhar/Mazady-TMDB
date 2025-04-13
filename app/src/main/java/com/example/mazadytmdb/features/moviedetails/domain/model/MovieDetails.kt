package com.example.mazadytmdb.features.moviedetails.domain.model

data class MovieDetails(
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String?,
    val backdropPath: String?,
    val releaseDate: String?,
    val voteAverage: Double,
    val runtime: Int,
    val genres: List<Genre>,
    val isFavorite: Boolean = false
) {
    data class Genre(
        val id: Int,
        val name: String
    )
}