package com.slicedwork.tmdbmovies.data.source.remote.response.movie

import com.slicedwork.tmdbmovies.data.model.Movie
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieResults(
    @Json(name = "id")
    val id: String,
    @Json(name = "original_title")
    val original_title: String?,
    @Json(name = "poster_path")
    val poster_path: String?,
    @Json(name = "overview")
    val overview: String?,
    @Json(name = "release_date")
    val release_date: String?
) {
    fun getMovieModel() = Movie(
        id = this.id,
        original_title = this.original_title,
        poster_path = this.poster_path,
        overview = this.overview,
        release_date = this.release_date
    )
}
