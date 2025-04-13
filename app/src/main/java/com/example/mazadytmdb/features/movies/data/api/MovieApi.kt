package com.example.mazadytmdb.features.movies.data.api

import com.example.mazadytmdb.core.network.ApiUrls
import com.example.mazadytmdb.features.movies.data.dto.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {
    @GET(ApiUrls.Movies.TOP_RATED)
    suspend fun getTopRatedMovies(@Query("page") page: Int,): Response<MovieResponse>
}