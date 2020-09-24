package com.popcorn.ui.movieDetail

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.popcorn.R
import com.popcorn.base.BaseFragment
import com.popcorn.domain.models.Movie
import com.popcorn.utilities.ImageLoader
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.movie_detail_fragment.*
import javax.inject.Inject

@AndroidEntryPoint
class MovieDetailFragment : BaseFragment<MovieDetailViewModel>() {

    @Inject
    lateinit var imageLoader: ImageLoader

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getMovieDetails()
    }

    override fun getLayoutId() = R.layout.movie_detail_fragment

    override fun subscribeToObservers() {
        viewModel.apply {
            loading.observe(this@MovieDetailFragment, Observer { handleLoaderVisibility(it) })
            error.observe(this@MovieDetailFragment, Observer { showMessage(it) })
            movieData.observe(this@MovieDetailFragment, Observer { showDetails(it) })
        }
    }

    private fun showDetails(it: Movie) {
        imageLoader.loadImage(ivMovie, it.image)
        tvMovieTitle.text = it.title
    }

    private fun handleLoaderVisibility(isVisible: Boolean) {
        loader.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    override fun createViewModel() = MovieDetailViewModel::class.java
}
