package com.popcorn.ui.usersList

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.popcorn.R
import com.popcorn.domain.models.Movie
import com.popcorn.utilities.ImageLoader
import com.popcorn.utilities.inflate
import kotlinx.android.synthetic.main.adapter_movie.view.*

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
        NowPlayingMoviesViewHolder(parent.inflate(R.layout.adapter_movie))

    override fun onBindViewHolder(holder: NowPlayingMoviesViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun getItemCount() = currentList.size

    inner class NowPlayingMoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener { userClickListener(currentList[layoutPosition]) }
        }

        fun bind(user: Movie) {
            itemView.apply {
                tvUsername.text = user.title
                tvEmail.text = user.title
                imageLoader.loadCircularImage(ivMovie, user.image)
            }
        }
    }
}
