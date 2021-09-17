package com.slicedwork.tmdbmovies.data.source.remote.response.credits

import com.slicedwork.tmdbmovies.data.model.Actor
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CreditsCast(
    @Json(name = "id")
    val id: String,
    @Json(name = "original_name")
    val original_name: String?,
    @Json(name = "character")
    val character: String?,
    @Json(name = "profile_path")
    val profile_path: String?
) {
    fun getActorModel() = Actor(
        id = this.id,
        original_name = this.original_name,
        character = this.character,
        profile_path = this.profile_path
    )
}
