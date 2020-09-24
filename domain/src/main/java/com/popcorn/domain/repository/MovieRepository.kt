package com.popcorn.domain.repository

import com.popcorn.domain.models.Movie
import com.popcorn.domain.models.MovieWrapper
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getMovie(id: Int): Flow<Movie>
    suspend fun getMovies(): Flow<MovieWrapper>
}
