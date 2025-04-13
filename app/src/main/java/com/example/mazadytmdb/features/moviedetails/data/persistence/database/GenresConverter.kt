package com.example.mazadytmdb.features.moviedetails.data.persistence.database

import androidx.room.TypeConverter
import com.example.mazadytmdb.features.moviedetails.domain.model.MovieDetails
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class GenreConverters {
    private val gson = Gson()

    @TypeConverter
    fun fromGenres(genres: List<MovieDetails.Genre>?): String? {
        return genres?.let { gson.toJson(it) }
    }

    @TypeConverter
    fun toGenres(json: String?): List<MovieDetails.Genre>? {
        return json?.let {
            val type = object : TypeToken<List<MovieDetails.Genre>>() {}.type
            gson.fromJson(json, type)
        }
    }
}