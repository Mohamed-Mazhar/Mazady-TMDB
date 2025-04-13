package com.example.mazadytmdb.features.movies.data.mapper

import com.example.mazadytmdb.features.moviedetails.data.persistence.database.MovieDetailsEntity
import com.example.mazadytmdb.features.moviedetails.domain.model.MovieDetails
import com.example.mazadytmdb.features.movies.data.dto.MovieDto
import com.example.mazadytmdb.features.movies.data.persistence.database.MovieEntity
import com.example.mazadytmdb.features.movies.domain.model.Movie

fun MovieDto.toDomainModel(): Movie {
    return Movie(
        id = id,
        title = title,
        posterUrl = posterPath.let { "https://image.tmdb.org/t/p/w500$it" },
        releaseDate = releaseDate
    )
}

fun Movie.toDatabaseEntity(): MovieEntity {
    return MovieEntity(
        id = id,
        title = title,
        localPosterPath = localPosterPath,
        isFavorite = isFavorite,
        posterUrl = posterUrl,
        releaseDate = releaseDate
    )
}

fun MovieEntity.toDomainModel(): Movie {
    return Movie(
        id = id,
        title = title,
        posterUrl = posterUrl,
        releaseDate = releaseDate,
        localPosterPath = localPosterPath,
        isFavorite = isFavorite
    )
}