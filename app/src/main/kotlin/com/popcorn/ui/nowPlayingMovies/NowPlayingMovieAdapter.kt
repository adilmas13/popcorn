package com.popcorn.ui.nowPlayingMovies

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.popcorn.R
import com.popcorn.databinding.AdapterMovieBinding
import com.popcorn.domain.models.Movie
import com.popcorn.helpers.ImageType
import com.popcorn.helpers.TransformationType
import com.popcorn.utilities.ImageLoader
import com.popcorn.utilities.inflate

class NowPlayingMovieAdapter(
    private val imageLoader: ImageLoader,
    val userClickListener: (Movie) -> Unit
) :
    ListAdapter<Movie, NowPlayingMovieAdapter.NowPlayingMoviesViewHolder>(DIFF_UTIL) {

    companion object {
        var DIFF_UTIL = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie) =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie) =
                oldItem.id == newItem.id &&
                    oldItem.title == newItem.title &&
                    oldItem.image == newItem.image
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        NowPlayingMoviesViewHolder(parent.inflate<AdapterMovieBinding>(R.layout.adapter_movie))

    override fun onBindViewHolder(holder: NowPlayingMoviesViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun getItemCount() = currentList.size

    inner class NowPlayingMoviesViewHolder(val binding: AdapterMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener { userClickListener(currentList[layoutPosition]) }
        }

        fun bind(movie: Movie) {
            binding.movie = movie
            imageLoader.load(
                binding.ivMovie,
                movie.image,
                ImageType.Poster,
                TransformationType.Normal
            )
        }
    }
}
