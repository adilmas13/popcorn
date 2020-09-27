package com.popcorn.domain

import kotlinx.coroutines.flow.Flow

interface LocalStorage {
    suspend fun setString(key: String, value: String)
    suspend fun getString(key: String, default: String = ""): Flow<String>
    suspend fun <T> setList(key: String, values: List<T>)
    suspend fun <T> getList(key: String, default: List<T>): Flow<List<T>>
}
