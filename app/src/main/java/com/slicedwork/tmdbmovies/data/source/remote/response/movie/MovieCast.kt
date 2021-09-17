package com.slicedwork.tmdbmovies.data.source.remote.response.movie

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieCast(
    @Json(name = "cast")
    val movieResults: List<MovieResults>
)
