package com.example.mazadytmdb.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mazadytmdb.features.movies.data.persistence.database.MovieEntity
import com.example.mazadytmdb.features.movies.data.persistence.database.MoviesDao


@Database(
    entities = [MovieEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun moviesDao(): MoviesDao
}