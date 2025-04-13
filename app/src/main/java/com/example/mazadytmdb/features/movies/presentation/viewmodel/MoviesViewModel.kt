package com.example.mazadytmdb.features.movies.presentation.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mazadytmdb.core.domain.ApiError
import com.example.mazadytmdb.core.domain.Result
import com.example.mazadytmdb.features.movies.data.repository.ImageRepository
import com.example.mazadytmdb.features.movies.data.repository.MoviesRepository
import com.example.mazadytmdb.features.movies.domain.model.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MoviesViewModel(
    private val moviesRepo: MoviesRepository,
    private val imageRepository: ImageRepository
) : ViewModel() {

    private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    val movies: StateFlow<List<Movie>> = _movies.asStateFlow()

    private val _localCachedMovies = MutableStateFlow<List<Movie>>(emptyList())

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private var currentPage = 1

    init {
        loadMovies()
    }

    private fun loadMovies() {
        if (_isLoading.value) return

        viewModelScope.launch {
            _isLoading.value = true
            try {
                _localCachedMovies.value = moviesRepo.getLocalMovies()
                when (val newMoviesResult = moviesRepo.getMovies(currentPage)) {
                    is Result.Success -> {
                        val combinedMovies = newMoviesResult.data.map { networkMovie ->
                            networkMovie.copy(
                                isFavorite = _localCachedMovies.value[networkMovie.id].isFavorite
                            )
                        }
                        _movies.update { currentMovies -> currentMovies + combinedMovies }
                        currentPage++
                        moviesRepo.insertMovies(newMoviesResult.data)
                    }

                    is Result.Error -> {
                        _error.value = when (newMoviesResult.apiError) {
                            is ApiError.NetworkError -> "Failed to load movies"
                            is ApiError.NoInternetError -> {
                                if (_localCachedMovies.value.isNotEmpty()) {
                                    _movies.value = _localCachedMovies.value
                                    null
                                } else {
                                    "No internet connection"
                                }
                            }

                            is ApiError.ServerError -> "Server error occurred"
                        }
                    }
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "Unknown error occurred"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun toggleFavorite(movie: Movie) {
        viewModelScope.launch {
            try {
                _movies.update { currentMovies ->
                    currentMovies.map {
                        if (it.id == movie.id) it.copy(isFavorite = !it.isFavorite) else it
                    }
                }
            } catch (e: Exception) {
                _error.value = "Failed to update favorites: ${e.message}"
                _movies.update { currentMovies ->
                    currentMovies.map {
                        if (it.id == movie.id) it.copy(isFavorite = movie.isFavorite) else it
                    }
                }
            }
        }
    }

    fun saveImageBitmap(movie: Movie, bitmap: Bitmap) {
        viewModelScope.launch {
            val imageLocalPath = imageRepository.saveImage(bitmap, "movie_${movie.id}.png")
            val updatedMovie = movie.copy(localPosterPath = imageLocalPath)
            moviesRepo.insertMovie(updatedMovie)
        }
    }

    fun loadMoreMovies() {
        loadMovies()
    }
}