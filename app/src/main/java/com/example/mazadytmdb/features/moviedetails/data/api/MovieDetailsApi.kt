package com.example.mazadytmdb.features.moviedetails.data.api

import com.example.mazadytmdb.core.network.ApiUrls
import com.example.mazadytmdb.features.moviedetails.data.dto.MovieDetailsDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieDetailsApi {

    @GET(ApiUrls.Movies.MOVIE_DETAILS)
    suspend fun getMovieDetails(@Path("id") id: Int): Response<MovieDetailsDto>

}