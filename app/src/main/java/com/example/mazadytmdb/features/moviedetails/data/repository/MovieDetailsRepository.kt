package com.example.mazadytmdb.features.moviedetails.data.repository

import com.example.mazadytmdb.core.domain.Result
import com.example.mazadytmdb.features.moviedetails.data.api.MovieDetailsApi
import com.example.mazadytmdb.features.moviedetails.data.mapper.toDomain
import com.example.mazadytmdb.features.moviedetails.domain.model.MovieDetails

class MovieDetailsRepository(private val movieDetailsApi: MovieDetailsApi) {

    suspend fun getMovieDetails(movieId: Int): Result<MovieDetails> {
        return try {
            val movieDetailsResponse = movieDetailsApi.getMovieDetails(movieId)
            if (movieDetailsResponse.isSuccessful) {
                movieDetailsResponse.body()?.let {
                    Result.Success(it.toDomain())
                } ?: Result.Error(message = "Empty response body")
            } else {
                Result.Error(
                    message = movieDetailsResponse.errorBody()?.string(),
                    code = movieDetailsResponse.code()
                )
            }
        } catch (e: Exception) {
            Result.Error(exception = e)
        }
    }

}