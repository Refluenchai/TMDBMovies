package com.slicedwork.tmdbmovies.util.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.slicedwork.tmdbmovies.data.repository.CreditsRepository
import com.slicedwork.tmdbmovies.viewmodel.MovieDetailsViewModel

class MovieDetailsViewModelFactory(private var creditsRepository: CreditsRepository) :
    ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieDetailsViewModel::class.java))
            return MovieDetailsViewModel(creditsRepository) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}