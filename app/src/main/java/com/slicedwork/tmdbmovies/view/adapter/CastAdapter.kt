package com.slicedwork.tmdbmovies.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.slicedwork.tmdbmovies.R
import com.slicedwork.tmdbmovies.data.model.Actor
import com.slicedwork.tmdbmovies.databinding.ItemCastBinding

class CastAdapter(
    private val actors: List<Actor>,
    private val onItemClickListener: (actor: Actor, view: View) -> Unit
) :
    RecyclerView.Adapter<CastAdapter.CastViewHolder>() {

    private lateinit var binding: ItemCastBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ItemCastBinding.inflate(inflater, parent, false)

        return CastViewHolder(binding, onItemClickListener)
    }

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {
        holder.bind(actors[position])
    }

    override fun getItemCount(): Int {
        return actors.size
    }

    inner class CastViewHolder(
        private val binding: ItemCastBinding,
        private val onItemClickListener: (actor: Actor, view: View) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        private val profilePath = binding.imgProfile
        private val originalName = binding.txtName
        private val character = binding.txtCharacter
        fun bind(actor: Actor) {

            originalName.text = actor.original_name
            character.text = actor.character

            Glide.with(itemView.context)
                .load("https://image.tmdb.org/t/p/w500" + actor.profile_path)
                .error(R.drawable.actordefault)
                .placeholder(R.drawable.actordefault)
                .into(profilePath)

            binding.root.setOnClickListener { view ->
                onItemClickListener.invoke(actor, view)
            }
        }
    }

}