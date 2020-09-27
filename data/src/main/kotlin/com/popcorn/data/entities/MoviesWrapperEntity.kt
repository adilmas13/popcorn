package com.popcorn.data.entities

import com.popcorn.domain.models.Movie
import com.popcorn.domain.models.MovieWrapper
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MoviesWrapperResponse(
    @SerialName("results") private val movies: List<MovieResponse>,
    @SerialName("page") private val page: Int,
    @SerialName("total_pages") private val totalPages: Int,
) {
    fun toMovieWrapper() = MovieWrapper(
        movies.map { it.toMovie() },
        page < totalPages
    )
}

@Serializable
data class MovieResponse(
    @SerialName("poster_path") private val posterPath: String,
    @SerialName("id") private val id: Int,
    @SerialName("backdrop_path") private val backdropPath: String,
    @SerialName("title") private val title: String,
    @SerialName("overview") private val overview: String,
    @SerialName("release_date") private val releaseDate: String,
    @SerialName("vote_average") private val voteAverage: String,
) {
    fun toMovie() = Movie(id, title, posterPath)
}
