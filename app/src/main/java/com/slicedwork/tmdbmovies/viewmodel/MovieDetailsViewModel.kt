package com.slicedwork.tmdbmovies.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.slicedwork.tmdbmovies.R
import com.slicedwork.tmdbmovies.data.model.Actor
import com.slicedwork.tmdbmovies.data.model.Technical
import com.slicedwork.tmdbmovies.data.repository.CreditsRepository
import com.slicedwork.tmdbmovies.data.results.Result

class MovieDetailsViewModel(private var creditsRepository: CreditsRepository) : ViewModel() {
    val castLiveData: MutableLiveData<List<Actor>> = MutableLiveData()
    val directorLiveData: MutableLiveData<Technical> = MutableLiveData()
    var errorLiveData: MutableLiveData<Int> = MutableLiveData()

    fun getCredits(movieId: String) {
        creditsRepository = CreditsRepository(movieId)
        creditsRepository.getCredits { result ->
            getCast(result)
            getCrew(result)
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun getCast(result: Result) {
        when (result) {
            is Result.Success -> castLiveData.postValue(result.list as List<Actor>)
            is Result.ApiError -> {
                errorLiveData.value = if (result.statusCode == 401) R.string.erro_401
                else R.string.erro_400_generic
            }
            is Result.ServerError -> errorLiveData.value = R.string.erro_500_generic
        }
    }
    private fun getCrew(result: Result) {
        when (result) {
            is Result.Success -> directorLiveData.postValue(result.any as Technical)
            is Result.ApiError -> {
                errorLiveData.value = if (result.statusCode == 401) R.string.erro_401
                else R.string.erro_400_generic
            }
            is Result.ServerError -> errorLiveData.value = R.string.erro_500_generic
        }
    }
}
