package com.popcorn.ui.nowPlayingMovies

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.popcorn.domain.models.Movie
import com.popcorn.domain.usecase.NowPlayingMoviesUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class NowPlayingMoviesViewModel @ViewModelInject constructor(
    private val nowPlayingMoviesUseCase: NowPlayingMoviesUseCase
) : ViewModel() {
    var movies: MutableLiveData<List<Movie>> = MutableLiveData()

    var loading: MutableLiveData<Boolean> = MutableLiveData()

    var errorMessage: MutableLiveData<String> = MutableLiveData()

    @ExperimentalCoroutinesApi
    fun getMovies() {
        viewModelScope.launch {
            nowPlayingMoviesUseCase
                .getMovies()
                .onStart { loading.value = true }
                .onCompletion { loading.value = false }
                .catch { errorMessage.value = it.message } // on error
                .collect { movies.value = it.movies }
        }
    }
}
