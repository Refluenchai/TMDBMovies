package com.slicedwork.tmdbmovies.util.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.slicedwork.tmdbmovies.data.repository.MoviesRepository
import com.slicedwork.tmdbmovies.data.repository.PersonRepository
import com.slicedwork.tmdbmovies.viewmodel.ActorDetailViewModel

class ActorDetailViewModelFactory(private val personRepository: PersonRepository, private val moviesRepository: MoviesRepository): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ActorDetailViewModel::class.java))
            return ActorDetailViewModel(personRepository, moviesRepository) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}