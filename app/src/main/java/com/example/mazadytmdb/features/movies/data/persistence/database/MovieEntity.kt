package com.example.mazadytmdb.features.movies.data.persistence.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val posterUrl: String,
    val releaseDate: String,
    val isFavorite: Boolean = false,
    val localPosterPath: String? = null
)