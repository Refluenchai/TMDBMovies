package com.slicedwork.tmdbmovies.data.source.remote.response.genre

import com.slicedwork.tmdbmovies.data.model.Genre
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GenreResults(
    @Json(name = "id")
    val id: String,
    @Json(name = "name")
    val name: String
) {
    fun getGenreModel() = Genre(
        id = this.id,
        name = this.name
    )
}
