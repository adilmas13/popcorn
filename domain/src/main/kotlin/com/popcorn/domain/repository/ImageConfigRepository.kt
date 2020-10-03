package com.popcorn.domain.repository

import kotlinx.coroutines.flow.Flow

interface ImageConfigRepository {
    suspend fun storeBaseUrl(url: String)
    suspend fun getBaseUrl(): Flow<String>
    suspend fun storeBackDrop(images: List<String>)
    suspend fun getBackDrop(): Flow<String>
    suspend fun storeLogo(images: List<String>)
    suspend fun getLogo(): Flow<String>
    suspend fun storePosters(images: List<String>)
    suspend fun getPosters(): Flow<String>
}
