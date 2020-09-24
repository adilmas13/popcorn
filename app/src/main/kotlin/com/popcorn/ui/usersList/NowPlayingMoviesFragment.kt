package com.popcorn.ui.usersList

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.popcorn.R
import com.popcorn.base.BaseFragment
import com.popcorn.utilities.ImageLoader
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.movies_list_fragment.*
import javax.inject.Inject

@AndroidEntryPoint
class NowPlayingMoviesFragment : BaseFragment<NowPlayingMoviesViewModel>() {

    @Inject
    lateinit var imageLoader: ImageLoader

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
        viewModel.getMovies()
    }

    private fun initViews() {
        rvUsers.adapter = NowPlayingMovieAdapter(imageLoader) { movie ->
            val action = NowPlayingMoviesFragmentDirections.showMovieDetails(movie)
            findNavController().navigate(action)
        }
    }

    override fun getLayoutId() = R.layout.movies_list_fragment

    override fun subscribeToObservers() {
        viewModel.loading.observe(this, { handleLoaderVisibility(it) })
        viewModel.movies.observe(
            this,
            { (rvUsers.adapter as NowPlayingMovieAdapter).submitList(it) }
        )
        viewModel.errorMessage.observe(this, { showMessage(it) })
    }

    private fun handleLoaderVisibility(isVisible: Boolean) {
        loader.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    override fun createViewModel() = NowPlayingMoviesViewModel::class.java
}
