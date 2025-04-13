package com.example.mazadytmdb.features.moviedetails.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mazadytmdb.core.domain.Result
import com.example.mazadytmdb.features.moviedetails.data.repository.MovieDetailsRepository
import com.example.mazadytmdb.features.moviedetails.domain.model.MovieDetails
import com.example.mazadytmdb.features.movies.domain.model.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MovieDetailsViewModel(private val movieDetailsRepository: MovieDetailsRepository) : ViewModel() {

    private val _movieDetails = MutableStateFlow<MovieDetails?>(null)
    val movieDetails: StateFlow<MovieDetails?> = _movieDetails.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    fun loadMovieDetails(movieId: Int) {
        _isLoading.value = true
        viewModelScope.launch {
            when (val result = movieDetailsRepository.getMovieDetails(movieId)) {
                is Result.Success -> {
                    _movieDetails.value = result.data // Success case
                }
                is Result.Error -> {
                    _errorMessage.value = result.message ?: "Unknown error"
                }
            }
            _isLoading.value = false
        }
    }

    fun toggleFavorite(movie: MovieDetails) {
        viewModelScope.launch {
            try {
                _movieDetails.update {
                   it?.copy(isFavorite = !it.isFavorite)
                }
            } catch (e: Exception) {
                _errorMessage.value = "Failed to update favorites: ${e.message}"
            }
        }
    }

}