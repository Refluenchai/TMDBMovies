package com.slicedwork.tmdbmovies.data.source.remote.response.genre

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GenreBody(
    @Json(name = "genres")
    val genreResults: List<GenreResults>
)
