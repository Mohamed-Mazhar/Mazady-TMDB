package com.example.mazadytmdb.features.moviedetails.data.mapper

import com.example.mazadytmdb.features.moviedetails.data.dto.MovieDetailsDto
import com.example.mazadytmdb.features.moviedetails.domain.model.MovieDetails

fun MovieDetailsDto.toDomain(isFavorite: Boolean = false): MovieDetails {
    return MovieDetails(
        id = id,
        title = title,
        overview = overview.ifEmpty { "No overview available" },
        posterPath = posterPath,
        backdropPath = backdropPath,
        releaseDate = releaseDate?.ifBlank { "Unknown date" },
        voteAverage = voteAverage,
        runtime = runtime ?: 0,
        genres = genreDtos.map { it.toDomain() },
        isFavorite = isFavorite
    )
}

fun MovieDetailsDto.GenreDto.toDomain(): MovieDetails.Genre {
    return MovieDetails.Genre(
        id = id,
        name = name
    )
}