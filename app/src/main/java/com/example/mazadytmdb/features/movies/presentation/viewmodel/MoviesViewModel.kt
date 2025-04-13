package com.example.mazadytmdb.features.movies.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mazadytmdb.core.domain.ApiError
import com.example.mazadytmdb.core.domain.Result
import com.example.mazadytmdb.features.movies.data.repository.MoviesRepository
import com.example.mazadytmdb.features.movies.domain.model.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MoviesViewModel(private val moviesRepo: MoviesRepository) : ViewModel() {

    private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    val movies: StateFlow<List<Movie>> = _movies.asStateFlow()

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
                when (val newMoviesResult = moviesRepo.getMovies(currentPage)) {
                    is Result.Success -> {
                        _movies.update { currentMovies -> currentMovies + newMoviesResult.data }
                        currentPage++
                    }
                    is Result.Error -> {
                        _error.value = when (newMoviesResult.apiError) {
                            is ApiError.NetworkError -> "Failed to load movies"
                            is ApiError.NoInternetError -> "No internet connection"
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

    fun loadMoreMovies() {
        loadMovies()
    }
}