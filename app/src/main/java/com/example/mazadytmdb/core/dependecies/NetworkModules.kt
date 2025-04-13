package com.example.mazadytmdb.core.dependecies

import com.example.mazadytmdb.core.network.provideOkHttpClient
import com.example.mazadytmdb.core.network.provideRetrofit
import com.example.mazadytmdb.features.moviedetails.data.api.MovieDetailsApi
import com.example.mazadytmdb.features.movies.data.api.MovieApi
import org.koin.dsl.module
import retrofit2.Retrofit

val networkModule = module {
    single { provideOkHttpClient() }
    single { provideRetrofit(get()) }
    single { get<Retrofit>().create(MovieApi::class.java) }
    single { get<Retrofit>().create(MovieDetailsApi::class.java) }

}
