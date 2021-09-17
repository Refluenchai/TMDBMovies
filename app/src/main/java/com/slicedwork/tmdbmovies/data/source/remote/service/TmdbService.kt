package com.slicedwork.tmdbmovies.data.source.remote.service

import com.slicedwork.tmdbmovies.data.source.remote.response.credits.CreditsBody
import com.slicedwork.tmdbmovies.data.source.remote.response.genre.GenreBody
import com.slicedwork.tmdbmovies.data.source.remote.response.movie.MovieBody
import com.slicedwork.tmdbmovies.data.source.remote.response.movie.MovieCast
import com.slicedwork.tmdbmovies.data.source.remote.response.person.PersonBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbService {
    @GET("discover/movie")
    fun getMoviesByGenres(
        @Query("with_genres") genre: String? = "",
        @Query("api_key") apiKey: String = "069749e8f645b64f3bd74d0ffd79d7f2",
        @Query("language") language: String = "pt-BR"
    ): Call<MovieBody>

    @GET("person/{person_id}/movie_credits")
    fun getMoviesByPerson(
        @Path("person_id") personId: String = "",
        @Query("api_key") apiKey: String = "069749e8f645b64f3bd74d0ffd79d7f2",
        @Query("language") language: String = "pt-BR"
    ): Call<MovieCast>

    @GET("genre/movie/list")
    fun getGenres(
        @Query("api_key") apiKey: String = "069749e8f645b64f3bd74d0ffd79d7f2",
        @Query("language") language: String = "pt-BR"
    ): Call<GenreBody>

    @GET("movie/{movie_id}/credits")
    fun getCredits(
        @Path("movie_id") movieId: String,
        @Query("api_key") apiKey: String = "069749e8f645b64f3bd74d0ffd79d7f2",
        @Query("language") language: String = "pt-BR"
    ): Call<CreditsBody>

    @GET("person/{person_id}")
    fun getPerson(
        @Path("person_id") personId: String,
        @Query("api_key") apiKey: String = "069749e8f645b64f3bd74d0ffd79d7f2",
        @Query("language") language: String = "pt-BR"
    ): Call<PersonBody>
}