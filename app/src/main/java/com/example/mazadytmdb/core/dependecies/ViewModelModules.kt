package com.example.mazadytmdb.core.dependecies

import com.example.mazadytmdb.features.moviedetails.presentation.viewmodel.MovieDetailsViewModel
import com.example.mazadytmdb.features.movies.presentation.viewmodel.MoviesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModules = module {
    viewModel { MoviesViewModel(get(), get()) }
    viewModel { MovieDetailsViewModel(get()) }
}