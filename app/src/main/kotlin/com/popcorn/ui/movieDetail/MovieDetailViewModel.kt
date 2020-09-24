package com.popcorn.ui.movieDetail

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.popcorn.domain.models.Movie
import com.popcorn.domain.usecase.MovieDetailUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class MovieDetailViewModel @ViewModelInject constructor(
    private val movieDetailUseCase: MovieDetailUseCase,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val movie = savedStateHandle.get<Movie>("movie")

    val loading = MutableLiveData<Boolean>()

    val movieData = MutableLiveData<Movie>()

    val error = MutableLiveData<String>()

    @ExperimentalCoroutinesApi
    fun getMovieDetails() {
        if (movie == null) return
        viewModelScope.launch {
            movieDetailUseCase.getMovieDetail(movie.id)
                .onStart { loading.value = true }
                .onCompletion { loading.value = false }
                .catch { error.value = it.message }
                .collect { movieData.value = it }
        }
    }
}
