package com.example.mazadytmdb.features.movies.data.repository

import com.example.mazadytmdb.core.domain.ApiError
import com.example.mazadytmdb.core.domain.Result
import com.example.mazadytmdb.features.movies.data.api.MovieApi
import com.example.mazadytmdb.features.movies.data.mapper.toDomainModel
import com.example.mazadytmdb.features.movies.domain.model.Movie
import java.net.UnknownHostException


class MoviesRepository(private val movieApi: MovieApi) {

    suspend fun getMovies(currentPage: Int): Result<List<Movie>> {
        return try {
            val moviesResponse = movieApi.getTopRatedMovies(currentPage)
            if (moviesResponse.isSuccessful) {
                moviesResponse.body()?.let { it ->
                    Result.Success(it.results.map { movieDto -> movieDto.toDomainModel() })
                } ?: Result.Error(ApiError.ServerError(moviesResponse.code(), "Empty response body"))

            } else {
                Result.Error(ApiError.ServerError(moviesResponse.code(), "Empty response body"))
            }
        } catch (e: UnknownHostException) {
            Result.Error(ApiError.NoInternetError)
        } catch (e: Exception) {
            Result.Error(ApiError.NetworkError(e))
        }
    }
}