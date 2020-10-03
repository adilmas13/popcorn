package com.popcorn.data.repository

import com.popcorn.domain.LocalStorage
import com.popcorn.domain.repository.ImageConfigRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalStorageImageConfigRepository @Inject constructor(
    private val storage: LocalStorage
) : ImageConfigRepository {

    companion object {
        private const val KEY_IMAGE_BASE_URL = "image_base_url"
        private const val KEY_BACKDROP_IMAGES = "backdrop_images"
        private const val KEY_LOGO_IMAGES = "logo_images"
        private const val KEY_POSTERS_IMAGES = "poster_images"
        private const val BLANK = ""
    }

    override suspend fun storeBaseUrl(url: String) {
        storage.setString(KEY_IMAGE_BASE_URL, url)
    }

    override suspend fun getBaseUrl(): Flow<String> {
        return storage.getString(KEY_IMAGE_BASE_URL, BLANK)
    }

    override suspend fun storeBackDrop(images: List<String>) {
        storage.setString(KEY_BACKDROP_IMAGES, images.lastOrNull() ?: BLANK)
    }

    override suspend fun getBackDrop(): Flow<String> {
        return storage.getString(KEY_BACKDROP_IMAGES, BLANK)
    }

    override suspend fun storeLogo(images: List<String>) {
        storage.setString(KEY_LOGO_IMAGES, images.lastOrNull() ?: BLANK)
    }

    override suspend fun getLogo(): Flow<String> {
        return storage.getString(KEY_LOGO_IMAGES, BLANK)
    }

    override suspend fun storePosters(images: List<String>) {
        storage.setString(KEY_POSTERS_IMAGES, images.lastOrNull() ?: BLANK)
    }

    override suspend fun getPosters(): Flow<String> {
        return storage.getString(KEY_POSTERS_IMAGES, BLANK)
    }
}
