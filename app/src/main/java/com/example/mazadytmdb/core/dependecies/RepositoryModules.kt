package com.example.mazadytmdb.core.dependecies

import com.example.mazadytmdb.features.moviedetails.data.repository.MovieDetailsRepository
import com.example.mazadytmdb.features.movies.data.repository.ImageRepository
import com.example.mazadytmdb.features.movies.data.repository.MoviesRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModules = module {
    single { MoviesRepository(get(), get()) }
    single { MovieDetailsRepository(get()) }
    single { ImageRepository(androidContext()) }
}