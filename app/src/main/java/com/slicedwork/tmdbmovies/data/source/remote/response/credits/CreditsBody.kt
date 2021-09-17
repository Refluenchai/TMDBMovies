package com.slicedwork.tmdbmovies.data.source.remote.response.credits

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CreditsBody(
    @Json(name = "cast")
    val creditsCast: List<CreditsCast>,
    @Json(name = "crew")
    val creditsCrew: List<CreditsCrew>
)
