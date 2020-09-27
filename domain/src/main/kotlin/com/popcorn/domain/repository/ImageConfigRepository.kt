package com.popcorn.domain.repository

import kotlinx.coroutines.flow.Flow

interface ImageConfigRepository {
    suspend fun storeBaseUrl(url: String)
    suspend fun getBaseUrl(): Flow<String>
}
