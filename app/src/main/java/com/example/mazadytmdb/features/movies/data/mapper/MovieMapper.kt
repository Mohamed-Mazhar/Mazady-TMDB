package com.example.mazadytmdb.features.movies.data.mapper

import com.example.mazadytmdb.features.movies.data.dto.MovieDto
import com.example.mazadytmdb.features.movies.domain.model.Movie

fun MovieDto.toDomainModel(): Movie {
    return Movie(
        id = id,
        title = title,
        posterUrl = posterPath.let { "https://image.tmdb.org/t/p/w500$it" },
        releaseDate = releaseDate
    )
}