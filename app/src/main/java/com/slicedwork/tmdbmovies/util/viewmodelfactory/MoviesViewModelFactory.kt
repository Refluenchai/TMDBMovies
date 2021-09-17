package com.slicedwork.tmdbmovies.util.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.slicedwork.tmdbmovies.data.repository.GenresRepository
import com.slicedwork.tmdbmovies.data.repository.MoviesRepository
import com.slicedwork.tmdbmovies.viewmodel.MoviesViewModel

class MoviesViewModelFactory(
    private val moviesRepository: MoviesRepository,
    private val genresRepository: GenresRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MoviesViewModel::class.java))
            return MoviesViewModel(moviesRepository, genresRepository) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}