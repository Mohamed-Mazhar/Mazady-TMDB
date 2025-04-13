package com.example.mazadytmdb.features.moviedetails.data.dto

import com.google.gson.annotations.SerializedName

data class MovieDetailsDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("release_date")
    val releaseDate: String?,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("vote_count")
    val voteCount: Int,
    @SerializedName("runtime")
    val runtime: Int?,
    @SerializedName("genres")
    val genreDtos: List<GenreDto>,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("original_title")
    val originalTitle: String,
    @SerializedName("tagline")
    val tagline: String?,
    @SerializedName("status")
    val status: String,
    @SerializedName("homepage")
    val homepage: String?,
    @SerializedName("imdb_id")
    val imdbId: String?,
    @SerializedName("revenue")
    val revenue: Long,
    @SerializedName("budget")
    val budget: Long,
    @SerializedName("adult")
    val adult: Boolean,
    @SerializedName("video")
    val video: Boolean,
    @SerializedName("popularity")
    val popularity: Double
) {
    data class GenreDto(
        @SerializedName("id")
        val id: Int,
        @SerializedName("name")
        val name: String
    )

}