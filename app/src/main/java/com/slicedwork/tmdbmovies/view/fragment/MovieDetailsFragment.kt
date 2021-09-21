package com.slicedwork.tmdbmovies.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.slicedwork.tmdbmovies.R
import com.slicedwork.tmdbmovies.databinding.FragmentMovieDetailsBinding
import com.slicedwork.tmdbmovies.data.model.Actor
import com.slicedwork.tmdbmovies.data.model.Movie
import com.slicedwork.tmdbmovies.data.model.Technical
import com.slicedwork.tmdbmovies.data.repository.CreditsRepository
import com.slicedwork.tmdbmovies.view.adapter.CastAdapter
import com.slicedwork.tmdbmovies.viewmodel.MovieDetailsViewModel
import com.slicedwork.tmdbmovies.viewmodel.MovieDetailsViewModel.MovieDetailsViewModelFactory

class MovieDetailsFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentMovieDetailsBinding
    private lateinit var movieDetailsViewModel: MovieDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)

        movieDetailsViewModel =
            ViewModelProvider(
                this,
                MovieDetailsViewModelFactory(CreditsRepository())
            ).get(MovieDetailsViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movie = MovieDetailsFragmentArgs.fromBundle(requireArguments()).movie
        movieDetailsViewModel.getCredits(movie.id.toString())

        movieDetailsViewModel.castLiveData.observe(this.viewLifecycleOwner, {
            it?.let { actors -> setActors(actors) }
        })

        movieDetailsViewModel.directorLiveData.observe(this.viewLifecycleOwner, {
            it?.let { director -> setMovieDetails(movie, director) }
        })

        movieDetailsViewModel.errorLiveData.observe(this.viewLifecycleOwner, {
                it?.let { error -> Log.e("Error", getString(error)) }
            })
    }

    override fun onResume() {
        super.onResume()

        binding.incLayout.flBtnHome.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        if (view == binding.incLayout.flBtnHome) goToMovies(view)
    }

    private fun goToMovies(view: View) {
        view.findNavController()
            .navigate(MovieDetailsFragmentDirections.actionMovieDetailsFragmentToMoviesFragment())
    }

    private fun goToActorDetails(actor: Actor, view: View) {
        view.findNavController().navigate(
            MovieDetailsFragmentDirections
                .actionMovieDetailsFragmentToActorDetailsFragment(actor)
        )
    }

    private fun setMovieDetails(movie: Movie, technical: Technical) {
        with(binding.incLayout) {
            Glide.with(this@MovieDetailsFragment)
                .load("https://image.tmdb.org/t/p/w500" + movie.poster_path)
                .error(R.drawable.moviedefault)
                .placeholder(R.drawable.moviedefault)
                .into(imgPhoto)

            txtCardTitle.text = movie.original_title
            txtCardSubtitle.text = technical.original_name
            txtCardDate.text = movie.release_date
            txtCardText.text = movie.overview
            txtTitle.text = getString(R.string.lbl_cast)
        }
    }

    private fun setActors(actors: List<Actor>) {
        with(binding.incLayout.rvThreeColumns) {
            layoutManager = GridLayoutManager(this.context, 3)
            adapter = CastAdapter(actors) { actor, view ->
                goToActorDetails(actor, view)
            }
        }
    }
}