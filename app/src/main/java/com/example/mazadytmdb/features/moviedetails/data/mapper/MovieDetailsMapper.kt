package com.example.mazadytmdb.features.moviedetails.data.mapper

import com.example.mazadytmdb.features.moviedetails.data.dto.MovieDetailsDto
import com.example.mazadytmdb.features.moviedetails.data.persistence.database.GenreConverters
import com.example.mazadytmdb.features.moviedetails.data.persistence.database.MovieDetailsEntity
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

fun MovieDetails.toDatabaseEntity() : MovieDetailsEntity {
    return MovieDetailsEntity(
        id = id,
        title = title,
        overview = overview.ifEmpty { "No overview available" },
        posterPath = posterPath,
        releaseDate = releaseDate?.ifBlank { "Unknown date" },
        voteAverage = voteAverage,
        runtime = runtime,
        genres = GenreConverters().fromGenres(this.genres),
        backDropPath = backdropPath,
        isFavorite = isFavorite
    )
}

fun MovieDetailsEntity.toDomainModel(): MovieDetails {
    return  MovieDetails(
        id = id,
        title = title,
        overview = overview.ifEmpty { "No overview available" },
        posterPath = posterPath,
        backdropPath = backDropPath,
        releaseDate = releaseDate?.ifBlank { "Unknown date" },
        voteAverage = voteAverage,
        runtime = runtime ?: 0,
        genres = GenreConverters().toGenres(this.genres) ?: emptyList(),
        isFavorite = isFavorite
    )
}