package com.popcorn.di

import com.popcorn.helpers.CoilImageLoader
import com.popcorn.utilities.ImageLoader
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideImageLoader(): ImageLoader =
        CoilImageLoader()
}
