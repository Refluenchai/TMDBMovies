package com.slicedwork.tmdbmovies.data.source.remote.response.movie

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieBody(
    @Json(name = "results")
    val movieResults: List<MovieResults>
)
