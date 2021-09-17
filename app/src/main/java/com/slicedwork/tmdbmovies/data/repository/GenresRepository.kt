package com.slicedwork.tmdbmovies.data.repository

import com.slicedwork.tmdbmovies.data.model.Genre
import com.slicedwork.tmdbmovies.data.source.remote.response.genre.GenreBody
import com.slicedwork.tmdbmovies.data.source.remote.api.TmdbApi
import com.slicedwork.tmdbmovies.data.results.Result
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class GenresRepository {
     fun getGenres(genreResultsCallBack: (result: Result) -> Unit) {
        TmdbApi.tmdbService.getGenres().enqueue(object : Callback,
            retrofit2.Callback<GenreBody> {
            override fun onResponse(call: Call<GenreBody>, response: Response<GenreBody>) {
                when {
                    response.isSuccessful -> {
                        val genres: MutableList<Any> = mutableListOf()
                        genres.add(Genre("", "All"))

                        response.body()?.let { genreBodyResponse ->
                            for (result in genreBodyResponse.genreResults) {
                                val genre = result.getGenreModel()
                                genres.add(genre)
                            }
                        }

                        genreResultsCallBack(Result.Success(genres))
                    }
                    else -> genreResultsCallBack(Result.ApiError(response.code()))
                }
            }

            override fun onFailure(call: Call<GenreBody>, t: Throwable) {
                genreResultsCallBack(Result.ServerError)
            }
        })
    }
}