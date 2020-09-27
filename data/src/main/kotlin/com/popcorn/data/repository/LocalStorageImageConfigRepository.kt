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
    }

    override suspend fun storeBaseUrl(url: String) {
        storage.setString(KEY_IMAGE_BASE_URL, url)
    }

    override suspend fun getBaseUrl(): Flow<String> {
        return storage.getString(KEY_IMAGE_BASE_URL, "")
    }

    override suspend fun storeBackDrop(images: List<String>) {
        storage.setList(KEY_BACKDROP_IMAGES, images)
    }

    override suspend fun getBackDrop(): Flow<List<String>> {
        return storage.getList(KEY_BACKDROP_IMAGES, emptyList())
    }

    override suspend fun storeLogo(images: List<String>) {
        storage.setList(KEY_LOGO_IMAGES, images)
    }

    override suspend fun getLogo(): Flow<List<String>> {
        return storage.getList(KEY_LOGO_IMAGES, emptyList())
    }

    override suspend fun storePosters(images: List<String>) {
        storage.setList(KEY_POSTERS_IMAGES, images)
    }

    override suspend fun getPosters(): Flow<List<String>> {
        return storage.getList(KEY_POSTERS_IMAGES, emptyList())
    }
}
