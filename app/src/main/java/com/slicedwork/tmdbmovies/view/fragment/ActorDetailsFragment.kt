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
import com.slicedwork.tmdbmovies.databinding.FragmentActorDetailsBinding
import com.slicedwork.tmdbmovies.data.model.Movie
import com.slicedwork.tmdbmovies.data.model.Person
import com.slicedwork.tmdbmovies.data.repository.MoviesRepository
import com.slicedwork.tmdbmovies.data.repository.PersonRepository
import com.slicedwork.tmdbmovies.view.adapter.MoviesAdapter
import com.slicedwork.tmdbmovies.viewmodel.ActorDetailViewModel
import com.slicedwork.tmdbmovies.viewmodel.ActorDetailViewModel.ActorDetailViewModelFactory

class ActorDetailsFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentActorDetailsBinding
    private lateinit var actorDetailViewModel: ActorDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentActorDetailsBinding.inflate(inflater, container, false)

        actorDetailViewModel = ViewModelProvider(
            this,
            ActorDetailViewModelFactory(
                PersonRepository(),
                MoviesRepository()
            )
        ).get(ActorDetailViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val actor = ActorDetailsFragmentArgs.fromBundle(requireArguments()).actor

        actorDetailViewModel.actorLiveData.observe(this.viewLifecycleOwner, {
            it?.let { person -> setActorDetails(person)}
        })

        actorDetailViewModel.moviesLiveData.observe(this.viewLifecycleOwner, {
            it?.let { movies -> setMovies(movies) }
        })

        actorDetailViewModel.errorLiveData.observe(this.viewLifecycleOwner, {
            it?.let { error -> Log.e("Error", getString(error)) }
        })

        actorDetailViewModel.getPerson(actor.id.toString())
    }

    override fun onResume() {
        super.onResume()

        binding.incLayout.flBtnHome.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        if (view == binding.incLayout.flBtnHome) goToMovies(view)
    }

    private fun goToMovies(view: View) {
        view.findNavController()
            .navigate(ActorDetailsFragmentDirections.actionActorDetailsFragmentToMoviesFragment())
    }

    private fun goToMovieDetails(movie: Movie, view: View) {
        view.findNavController()
            .navigate(
                ActorDetailsFragmentDirections
                    .actionActorDetailsFragmentToMovieDetailsFragment(movie)
            )
    }

    private fun setActorDetails(person: Person) {
        with(binding.incLayout) {
            Glide.with(this@ActorDetailsFragment)
                .load("https://image.tmdb.org/t/p/w500" + person.profile_path)
                .error(R.drawable.actordefault)
                .placeholder(R.drawable.actordefault)
                .into(imgPhoto)

            txtCardTitle.text = person.name
            txtCardSubtitle.text = person.place_of_birth
            txtCardDate.text = person.birthday
            txtCardText.text = person.biography
            txtTitle.text = getString(R.string.lbl_movies)
            actorDetailViewModel.getMovies(person.id)
        }
    }

    private fun setMovies(movies: List<Movie>) {
        with(binding.incLayout.rvThreeColumns) {
            layoutManager = GridLayoutManager(this.context, 2)
            setHasFixedSize(true)
            adapter = MoviesAdapter(movies) { movie, view ->
                goToMovieDetails(
                    movie,
                    view
                )
            }
        }
    }

}