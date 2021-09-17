package com.slicedwork.tmdbmovies.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.slicedwork.tmdbmovies.R
import com.slicedwork.tmdbmovies.data.model.Movie
import com.slicedwork.tmdbmovies.data.model.Person
import com.slicedwork.tmdbmovies.data.repository.MoviesRepository
import com.slicedwork.tmdbmovies.data.repository.PersonRepository
import com.slicedwork.tmdbmovies.data.results.Result

class ActorDetailViewModel(
    private var personRepository: PersonRepository,
    private var moviesRepository: MoviesRepository
) : ViewModel() {
    val actorLiveData: MutableLiveData<Person> = MutableLiveData()
    val moviesLiveData: MutableLiveData<List<Movie>> = MutableLiveData()
    var errorLiveData: MutableLiveData<Int> = MutableLiveData()

    fun getPerson(actorId: String) {
        personRepository = PersonRepository(actorId)
        personRepository.getPerson { result ->
            when (result) {
                is Result.Success -> actorLiveData.value = result.any as Person
                is Result.ApiError -> {
                    errorLiveData.value = if (result.statusCode == 401) R.string.erro_401
                    else R.string.erro_400_generic
                }
                is Result.ServerError -> errorLiveData.value = R.string.erro_500_generic
            }
        }
    }

    fun getMovies(personId: String) {
        moviesRepository = MoviesRepository("", personId)
        moviesRepository.getMoviesByPerson { result ->
            @Suppress("UNCHECKED_CAST")
            when (result) {
                is Result.Success -> moviesLiveData.value = result.list as List<Movie>
                is Result.ApiError -> {
                    errorLiveData.value = if (result.statusCode == 401) R.string.erro_401
                    else R.string.erro_400_generic
                }
                is Result.ServerError -> errorLiveData.value = R.string.erro_500_generic
            }
        }
    }
}