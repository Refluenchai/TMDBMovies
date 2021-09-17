package com.slicedwork.tmdbmovies.data.source.remote.response.credits

import com.slicedwork.tmdbmovies.data.model.Technical
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CreditsCrew(
    @Json(name = "id")
    val id: String,
    @Json(name = "original_name")
    val original_name: String,
    @Json(name = "job")
    val job: String
) {
    fun getTechnicalModel() = Technical(
        id = this.id,
        original_name = this.original_name,
        job = this.job
    )
}
