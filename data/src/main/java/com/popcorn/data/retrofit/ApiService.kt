package com.popcorn.data.retrofit

import com.popcorn.data.entities.ConfigWrapperResponse
import com.popcorn.data.entities.MovieResponse
import com.popcorn.data.entities.MoviesWrapperResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    companion object {
        const val CONFIG = "configuration"
        const val GET_NOW_PLAYING_MOVIES = "movie/now_playing"
        const val GET_MOVIE = "movie"
    }

    @GET(CONFIG)
    suspend fun getConfig(): Response<ConfigWrapperResponse>

    @GET(GET_NOW_PLAYING_MOVIES)
    suspend fun getNowPlayingMovies(): Response<MoviesWrapperResponse>

    @GET("$GET_MOVIE/{id}")
    suspend fun getMovie(@Path("id") id: Int): Response<MovieResponse>
}
