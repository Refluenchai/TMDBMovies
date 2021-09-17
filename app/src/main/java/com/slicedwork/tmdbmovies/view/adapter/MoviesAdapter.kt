package com.slicedwork.tmdbmovies.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.slicedwork.tmdbmovies.R
import com.slicedwork.tmdbmovies.data.model.Movie
import com.slicedwork.tmdbmovies.databinding.ItemMovieBinding

class MoviesAdapter(private val movies: List<Movie>, private val onItemClickListener: (movie: Movie, view: View) -> Unit): RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {

    private lateinit var binding: ItemMovieBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ItemMovieBinding.inflate(inflater, parent, false)

        return MoviesViewHolder(binding, onItemClickListener)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    inner class MoviesViewHolder(
        private val binding: ItemMovieBinding,
        private val onItemClickListener: (movie: Movie, view: View) -> Unit
    ): RecyclerView.ViewHolder(binding.root) {
        private val originalTitle = binding.txtTitle
        private val posterPath = binding.imgMovie

        fun bind(movie: Movie) {
            originalTitle.text = movie.original_title

            Glide.with(itemView.context)
                .load("https://image.tmdb.org/t/p/w500" + movie.poster_path)
                .error(R.drawable.moviedefault)
                .placeholder(R.drawable.moviedefault)
                .into(posterPath)

            binding.root.setOnClickListener { view ->
                onItemClickListener.invoke(movie, view)
            }
        }
    }
}
