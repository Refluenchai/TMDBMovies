package com.slicedwork.tmdbmovies.data.repository

import android.util.Log
import com.slicedwork.tmdbmovies.data.source.remote.response.movie.MovieBody
import com.slicedwork.tmdbmovies.data.source.remote.response.movie.MovieCast
import com.slicedwork.tmdbmovies.data.source.remote.api.TmdbApi
import com.slicedwork.tmdbmovies.data.results.Result
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class MoviesRepository(private val genreId: String = "", private val personId: String = "") {
    fun getMoviesByGenres(movieResultsCallBack: (result: Result) -> Unit) {
        TmdbApi.tmdbService.getMoviesByGenres(genreId)
            .enqueue(object : Callback, retrofit2.Callback<MovieBody> {
                override fun onResponse(
                    call: Call<MovieBody>,
                    response: Response<MovieBody>
                ) {
                    when {
                        response.isSuccessful -> {
                            val movies: MutableList<Any> = mutableListOf()

                            response.body()?.let { movieBodyResponse ->
                                for (result in movieBodyResponse.movieResults) {
                                    val movie = result.getMovieModel()
                                    movies.add(movie)
                                }
                            }

                            movieResultsCallBack(Result.Success(movies))
                        }
                        else -> movieResultsCallBack(Result.ApiError(response.code()))
                    }
                }

                override fun onFailure(call: Call<MovieBody>, t: Throwable) {
                    movieResultsCallBack(Result.ServerError)
                }
            })
    }

    fun getMoviesByPerson(movieResultsCallBack: (result: Result) -> Unit) {
        TmdbApi.tmdbService.getMoviesByPerson(personId)
            .enqueue(object : Callback, retrofit2.Callback<MovieCast> {
                override fun onResponse(call: Call<MovieCast>, response: Response<MovieCast>) {
                    when {
                        response.isSuccessful -> {
                            val movies: MutableList<Any> = mutableListOf()

                            response.body()?.let { MovieCastResponse ->
                                for (result in MovieCastResponse.movieResults) {
                                    val movie = result.getMovieModel()
                                    movies.add(movie)
                                }
                            }
                            movieResultsCallBack(Result.Success(movies))
                        }
                        else -> {
                            movieResultsCallBack(Result.ApiError(response.code()))
                            Log.e("Error", call.request().url().toString())
                        }
                    }
                }

                override fun onFailure(call: Call<MovieCast>, t: Throwable) {
                    movieResultsCallBack(Result.ServerError)
                }
            })
    }
}