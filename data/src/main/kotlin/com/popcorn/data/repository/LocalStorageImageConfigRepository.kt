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
    }

    override suspend fun storeBaseUrl(url: String) {
        storage.setString(KEY_IMAGE_BASE_URL, url)
    }

    override suspend fun getBaseUrl(): Flow<String> {
        return storage.getString(KEY_IMAGE_BASE_URL, "")
    }
}
