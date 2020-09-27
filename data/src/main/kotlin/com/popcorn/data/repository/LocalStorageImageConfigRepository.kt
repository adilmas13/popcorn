package com.popcorn.data.repository

import com.popcorn.domain.LocalStorage
import com.popcorn.domain.repository.ImageConfigRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalStorageImageConfigRepository @Inject constructor(
    private val storage: LocalStorage
) : ImageConfigRepository {

    private var backdrop = listOf<String>()

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
        storage.setString(KEY_BACKDROP_IMAGES, images.toLocalStorageString())
    }

    override suspend fun getBackDrop(): Flow<List<String>> {
        if (backdrop.isNotEmpty()) {
            return flowOf(backdrop)
        }
        return storage.getString(KEY_BACKDROP_IMAGES, "").map {
            val images = it.fromLocalStorageString()
            backdrop = images
            images
        }
    }

    override suspend fun storeLogo(images: List<String>) {
        storage.setString(KEY_LOGO_IMAGES, images.toLocalStorageString())
    }

    override suspend fun getLogo(): Flow<List<String>> {
        return storage.getString(KEY_LOGO_IMAGES, "").map { it.fromLocalStorageString() }
    }

    override suspend fun storePosters(images: List<String>) {
        storage.setString(KEY_POSTERS_IMAGES, images.toLocalStorageString())
    }

    override suspend fun getPosters(): Flow<List<String>> {
        return storage.getString(KEY_POSTERS_IMAGES, "").map { it.fromLocalStorageString() }
    }

    private fun List<String>.toLocalStorageString() =
        this.toString().trimEnd { it == ']' }.trimStart { it == '[' }.replace(" ", "")

    private fun String.fromLocalStorageString() = this.split(",")
}
