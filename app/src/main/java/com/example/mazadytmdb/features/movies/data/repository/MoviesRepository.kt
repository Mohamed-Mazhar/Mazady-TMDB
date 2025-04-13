package com.example.mazadytmdb.features.movies.data.repository

import com.example.mazadytmdb.core.domain.ApiError
import com.example.mazadytmdb.core.domain.Result
import com.example.mazadytmdb.features.movies.data.api.MovieApi
import com.example.mazadytmdb.features.movies.data.mapper.toDatabaseEntity
import com.example.mazadytmdb.features.movies.data.mapper.toDomainModel
import com.example.mazadytmdb.features.movies.data.persistence.database.MoviesDao
import com.example.mazadytmdb.features.movies.domain.model.Movie
import java.net.UnknownHostException


class MoviesRepository(
    private val movieApi: MovieApi,
    private val moviesDao: MoviesDao
) {

    suspend fun getMovies(currentPage: Int): Result<List<Movie>> {
        return try {
            val moviesResponse = movieApi.getTopRatedMovies(currentPage)
            if (moviesResponse.isSuccessful) {
                moviesResponse.body()?.let {
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

    suspend fun insertMovie(movie: Movie) {
        return moviesDao.insertMovie(movie.toDatabaseEntity())
    }

    suspend fun insertMovies(movies: List<Movie>) {
        return moviesDao.insertMovies(movies.map { it.toDatabaseEntity() })
    }

    suspend fun getLocalMovies(): List<Movie> {
        return moviesDao.getAllMovies().map { it.toDomainModel() }
    }
}