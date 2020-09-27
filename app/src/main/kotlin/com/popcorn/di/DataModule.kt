package com.popcorn.di

import android.content.Context
import androidx.datastore.preferences.createDataStore
import com.popcorn.BuildConfig
import com.popcorn.data.repository.LocalStorageImageConfigRepository
import com.popcorn.data.repository.RetrofitConfigRepository
import com.popcorn.data.repository.RetrofitMovieRepository
import com.popcorn.data.retrofit.ApiServiceBuilder
import com.popcorn.data.storage.DataStoreLocalStorage
import com.popcorn.domain.LocalStorage
import com.popcorn.domain.NetworkMonitor
import com.popcorn.domain.repository.ConfigRepository
import com.popcorn.domain.repository.ImageConfigRepository
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

    @Binds
    abstract fun bindImageConfigRepository(repository: LocalStorageImageConfigRepository): ImageConfigRepository
}

@Module
@InstallIn(ApplicationComponent::class)
abstract class StorageModule {

    @Binds
    abstract fun bindLocalStorage(storage: DataStoreLocalStorage): LocalStorage
}

@Module
@InstallIn(ApplicationComponent::class)
object DataStorageModule {
    @Provides
    @Singleton
    fun provideDataStore(
        @ApplicationContext context: Context,
        @AppId appId: String
    ) = context.createDataStore(
        name = "$appId.popcorn"
    )

    @Provides
    @Singleton
    @AppId
    fun provideAppId() = BuildConfig.APPLICATION_ID

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class AppId
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
