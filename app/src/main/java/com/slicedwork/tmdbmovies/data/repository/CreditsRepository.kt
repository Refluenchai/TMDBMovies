package com.slicedwork.tmdbmovies.data.repository

import com.slicedwork.tmdbmovies.data.model.Technical
import com.slicedwork.tmdbmovies.data.source.remote.response.credits.CreditsBody
import com.slicedwork.tmdbmovies.data.source.remote.api.TmdbApi
import com.slicedwork.tmdbmovies.data.results.Result
import com.slicedwork.tmdbmovies.util.TmdbApplication
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class CreditsRepository(private val movieId: String = "") {
    fun getCredits(creditsResultsCallback: (result: Result) -> Unit) {
        TmdbApi.tmdbService.getCredits(movieId, TmdbApplication.apiKey, TmdbApplication.language)
            .enqueue(object : Callback,
                retrofit2.Callback<CreditsBody> {
                override fun onResponse(call: Call<CreditsBody>, response: Response<CreditsBody>) {
                    when {
                        response.isSuccessful -> {
                            val actors: MutableList<Any> = mutableListOf()
                            var technical: Technical
                            response.body()?.let { creditsBodyResponse ->
                                for (cast in creditsBodyResponse.creditsCast) {
                                    val actor = cast.getActorModel()
                                    actors.add(actor)
                                }

                                for (crew in creditsBodyResponse.creditsCrew) {
                                    technical = crew.getTechnicalModel()
                                    if (technical.job == "Director") {
                                        creditsResultsCallback(Result.Success(actors, technical))
                                        break
                                    }
                                }
                            }
                        }
                        else -> creditsResultsCallback(Result.ApiError(response.code()))
                    }
                }

                override fun onFailure(call: Call<CreditsBody>, t: Throwable) {
                    creditsResultsCallback(Result.ServerError)
                }
            })
    }
}