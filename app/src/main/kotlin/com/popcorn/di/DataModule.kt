package com.popcorn.di

import android.content.Context
import com.popcorn.BuildConfig
import com.popcorn.data.repository.RetrofitConfigRepository
import com.popcorn.data.repository.RetrofitMovieRepository
import com.popcorn.data.retrofit.ApiServiceBuilder
import com.popcorn.domain.NetworkMonitor
import com.popcorn.domain.repository.ConfigRepository
import com.popcorn.domain.repository.MovieRepository
import com.popcorn.helpers.LiveConnectivityMonitor
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindConfigRepository(repository: RetrofitConfigRepository): ConfigRepository

    @Binds
    abstract fun bindMovieRepository(repository: RetrofitMovieRepository): MovieRepository
}

@Module
@InstallIn(ApplicationComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideNetworkMonitor(@ApplicationContext context: Context): NetworkMonitor =
        LiveConnectivityMonitor(context)

    @Provides
    @Singleton
    fun provideRetrofitApiService(
        networkMonitor: NetworkMonitor,
        @BaseUrl baseUrl: String,
        @ApiKey apiKey: String
    ) =
        ApiServiceBuilder(networkMonitor, baseUrl, apiKey).build()

    @Provides
    @Singleton
    @BaseUrl
    fun provideBaseUrl() = BuildConfig.BASE_URL

    @Provides
    @Singleton
    @ApiKey
    fun provideApiKey() = BuildConfig.API_KEY

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class BaseUrl

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class ApiKey
}
