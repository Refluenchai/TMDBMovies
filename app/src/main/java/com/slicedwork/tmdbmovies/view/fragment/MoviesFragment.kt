package com.slicedwork.tmdbmovies.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.slicedwork.tmdbmovies.R
import com.slicedwork.tmdbmovies.databinding.FragmentMoviesBinding
import com.slicedwork.tmdbmovies.data.model.Genre
import com.slicedwork.tmdbmovies.data.model.Movie
import com.slicedwork.tmdbmovies.data.repository.GenresRepository
import com.slicedwork.tmdbmovies.data.repository.MoviesRepository
import com.slicedwork.tmdbmovies.view.adapter.MoviesAdapter
import com.slicedwork.tmdbmovies.viewmodel.MoviesViewModel
import com.slicedwork.tmdbmovies.util.viewmodelfactory.MoviesViewModelFactory

class MoviesFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private lateinit var binding: FragmentMoviesBinding
    private lateinit var genres: List<Genre>
    private lateinit var moviesViewModel: MoviesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentMoviesBinding.inflate(inflater, container, false)

        moviesViewModel = ViewModelProvider(
            this,
            MoviesViewModelFactory(MoviesRepository(""), GenresRepository())
        ).get(MoviesViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        moviesViewModel.moviesLiveData.observe(this.viewLifecycleOwner, {
            it?.let { movies -> setMovies(movies) }
        })

        moviesViewModel.genresLiveData.observe(this.viewLifecycleOwner, {
            it?.let { genres -> setGenres(genres) }
        })

        moviesViewModel.errorLiveData.observe(this.viewLifecycleOwner, {
            it?.let { error -> Log.e("Error", getString(error)) }
        })

        moviesViewModel.getMovies()
        moviesViewModel.getGenres()
    }

    override fun onResume() {
        super.onResume()

        binding.spGenres.onItemSelectedListener = this
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, p3: Long) {

        for (genre in genres) {
            if (genre.name == parent?.getItemAtPosition(position).toString()) {
                moviesViewModel.getMovies(genre.id)
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}

    private fun goToDetails(movie: Movie, view: View) {
        view.findNavController()
            .navigate(MoviesFragmentDirections
                .actionMoviesFragmentToMovieDetailsFragment(movie))
    }

    private fun setGenres(genres: List<Any>) {
        with(binding.spGenres) {
            @Suppress("UNCHECKED_CAST")
            this@MoviesFragment.genres = genres as List<Genre>
            val genresNames = genres.map { genres -> genres.name }
            adapter = ArrayAdapter(
                this@MoviesFragment.requireContext(),
                R.layout.support_simple_spinner_dropdown_item,
                genresNames
            )
        }
    }

    private fun setMovies(movies: List<Any>) {
        @Suppress("UNCHECKED_CAST")
        with(binding.rvMovies) {
            layoutManager = GridLayoutManager(this.context, 2)
            setHasFixedSize(true)
            adapter = MoviesAdapter(movies as List<Movie>) { movie, view ->
                goToDetails(movie, view)
            }
        }
    }
}