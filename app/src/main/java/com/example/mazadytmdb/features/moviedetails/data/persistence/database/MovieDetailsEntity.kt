package com.example.mazadytmdb.features.moviedetails.data.persistence.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mazadytmdb.features.moviedetails.domain.model.MovieDetails.Genre

@Entity(tableName = "movie_details")
class MovieDetailsEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String?,
    val backDropPath: String?,
    val releaseDate: String?,
    val voteAverage: Double,
    val isFavorite: Boolean = false,
    val runtime: Int?,
    val genres: String?,
)