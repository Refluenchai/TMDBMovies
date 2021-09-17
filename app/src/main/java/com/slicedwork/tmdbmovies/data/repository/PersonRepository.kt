package com.slicedwork.tmdbmovies.data.repository

import com.slicedwork.tmdbmovies.data.model.Person
import com.slicedwork.tmdbmovies.data.source.remote.response.person.PersonBody
import com.slicedwork.tmdbmovies.data.source.remote.api.TmdbApi
import com.slicedwork.tmdbmovies.data.results.Result
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class PersonRepository(private val actorId: String = "") {
    fun getPerson(personResultsCallback: (result: Result) -> Unit) {
        TmdbApi.tmdbService.getPerson(actorId).enqueue(object: Callback,
            retrofit2.Callback<PersonBody>{
            override fun onResponse(call: Call<PersonBody>, response: Response<PersonBody>) {
                when {
                    response.isSuccessful -> {
                        val person: Person

                        response.body()?.let { personBodyResponse ->
                            person = personBodyResponse.getPersonModel()
                            personResultsCallback(Result.Success(person))
                        }
                    }
                    else -> personResultsCallback(Result.ApiError(response.code()))
                }
            }

            override fun onFailure(call: Call<PersonBody>, t: Throwable) {
                personResultsCallback(Result.ServerError)
            }
        }
        )
    }
}