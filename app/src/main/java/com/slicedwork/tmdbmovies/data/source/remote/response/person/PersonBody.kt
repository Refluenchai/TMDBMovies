package com.slicedwork.tmdbmovies.data.source.remote.response.person

import com.slicedwork.tmdbmovies.data.model.Person
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PersonBody(
    @Json(name = "id")
    val id: String,
    @Json(name = "name")
    val name: String?,
    @Json(name = "place_of_birth")
    val place_of_birth: String?,
    @Json(name = "birthday")
    val birthday: String?,
    @Json(name = "biography")
    val biography: String?,
    @Json(name = "profile_path")
    val profile_path: String?
) {
    fun getPersonModel() = Person(
        id,
        name,
        place_of_birth,
        birthday,
        biography,
        profile_path
    )
}