package com.popcorn.domain.models

import java.io.Serializable

data class Movie(
    val id: Int,
    val title: String,
    val image: String,
) : Serializable

data class MovieWrapper(
    val movies: List<Movie>,
    val hasMore: Boolean
)
