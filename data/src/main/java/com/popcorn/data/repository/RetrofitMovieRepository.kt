package com.popcorn.data.repository

import com.popcorn.data.repository.base.BaseRestApiRepository
import com.popcorn.data.retrofit.ApiService
import com.popcorn.domain.models.Movie
import com.popcorn.domain.models.MovieWrapper
import com.popcorn.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RetrofitMovieRepository @Inject constructor(
    private val api: ApiService
) : MovieRepository,
    BaseRestApiRepository() {

    override suspend fun getMovies(): Flow<MovieWrapper> {
        return parseResult(api.getNowPlayingMovies()) { response -> response.toMovieWrapper() }
    }

    override suspend fun getMovie(id: Int): Flow<Movie> {
        return parseResult(api.getMovie(id)) { it.toMovie() }
    }
}
