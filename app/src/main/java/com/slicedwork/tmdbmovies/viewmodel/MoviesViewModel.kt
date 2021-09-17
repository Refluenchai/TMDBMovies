package com.slicedwork.tmdbmovies.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.slicedwork.tmdbmovies.R
import com.slicedwork.tmdbmovies.data.repository.GenresRepository
import com.slicedwork.tmdbmovies.data.repository.MoviesRepository
import com.slicedwork.tmdbmovies.data.results.Result

class MoviesViewModel(
    private var moviesRepository: MoviesRepository,
    private val genresRepository: GenresRepository
) : ViewModel() {
    val moviesLiveData: MutableLiveData<List<Any>> = MutableLiveData()
    val genresLiveData: MutableLiveData<List<Any>> = MutableLiveData()
    var errorLiveData: MutableLiveData<Int> = MutableLiveData()

    fun getMovies(genreId: String = "") {
        moviesRepository = MoviesRepository(genreId)
        moviesRepository.getMoviesByGenres { result: Result -> getResult(result, moviesLiveData)}
    }

    fun getGenres() {
        genresRepository.getGenres { result: Result -> getResult(result, genresLiveData) }
    }

    private fun getResult(result: Result, liveData: MutableLiveData<List<Any>>) {
        when (result) {
            is Result.Success -> liveData.value = result.list
            is Result.ApiError -> {
                errorLiveData.value = if (result.statusCode == 401) R.string.erro_401
                else R.string.erro_400_generic
            }
            is Result.ServerError -> errorLiveData.value = R.string.erro_500_generic
        }
    }
}