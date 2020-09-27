package com.popcorn.domain

import kotlinx.coroutines.flow.Flow

interface LocalStorage {
    suspend fun setString(key: String, value: String)
    suspend fun getString(key: String, default: String = ""): Flow<String>
}
