package com.example.mazadytmdb.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mazadytmdb.features.moviedetails.data.persistence.database.GenreConverters
import com.example.mazadytmdb.features.moviedetails.data.persistence.database.MovieDetailsDao
import com.example.mazadytmdb.features.movies.data.persistence.database.MovieEntity
import com.example.mazadytmdb.features.movies.data.persistence.database.MoviesDao


@Database(
    entities = [MovieEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(GenreConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun moviesDao(): MoviesDao
    abstract fun moviesDetailsDao(): MovieDetailsDao
}