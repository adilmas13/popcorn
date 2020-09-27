package com.popcorn.domain.repository

import kotlinx.coroutines.flow.Flow

interface ConfigRepository {
    suspend fun getConfig(): Flow<Unit>
}
