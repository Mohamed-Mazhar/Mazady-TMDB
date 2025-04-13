package com.example.mazadytmdb.core.dependecies

import com.example.mazadytmdb.features.moviedetails.data.repository.MovieDetailsRepository
import com.example.mazadytmdb.features.movies.data.repository.MoviesRepository
import org.koin.dsl.module

val repositoryModules = module {
    single { MoviesRepository(get()) }
    single { MovieDetailsRepository(get()) }
}