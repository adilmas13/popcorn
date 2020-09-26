package com.popcorn.domain.usecase

import com.popcorn.domain.base.UseCase
import com.popcorn.domain.models.MovieWrapper
import com.popcorn.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NowPlayingMoviesUseCase @Inject constructor(private val repository: MovieRepository) :
    UseCase<MovieWrapper>() {

    override suspend fun makeRequest(): Flow<MovieWrapper> {
        return repository.getMovies()
    }

    suspend fun getMovies(): Flow<MovieWrapper> {
        return execute()
    }
}
