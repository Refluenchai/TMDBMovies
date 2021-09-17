package com.slicedwork.tmdbmovies.data.source.remote.api

import com.slicedwork.tmdbmovies.data.source.remote.service.TmdbService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object TmdbApi {
    private fun initRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(
                MoshiConverterFactory.create(
                Moshi.Builder()
                    .add(KotlinJsonAdapterFactory())
                    .build()
            ))
            .build()
    }

    val tmdbService: TmdbService = initRetrofit().create(TmdbService::class.java)
}