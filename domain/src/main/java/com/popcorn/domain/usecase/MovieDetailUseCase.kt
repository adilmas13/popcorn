package com.popcorn.domain.usecase

import com.popcorn.domain.base.UseCase
import com.popcorn.domain.models.Movie
import com.popcorn.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import kotlin.properties.Delegates

class MovieDetailUseCase @Inject constructor(
    private val repository: MovieRepository
) : UseCase<Movie>() {

    private var id by Delegates.notNull<Int>()

    override suspend fun makeRequest() = repository.getMovie(id)

    suspend fun getMovieDetail(id: Int): Flow<Movie> {
        this.id = id
        return execute()
    }
}
