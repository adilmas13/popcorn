package com.popcorn.data.storage

import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import com.popcorn.domain.LocalStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class DataStoreLocalStorage @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : LocalStorage {

    val json = Json { ignoreUnknownKeys = true }

    override suspend fun setString(key: String, value: String) {
        dataStore.edit {
            val prefKey = preferencesKey<String>(key)
            it[prefKey] = value
        }
    }

    override suspend fun getString(key: String, default: String): Flow<String> {
        val prefKey = preferencesKey<String>(key)
        return dataStore.data.map { it[prefKey] ?: default }
    }

    override suspend fun <T> setList(key: String, values: List<T>) {
        dataStore.edit {
            val prefKey = preferencesKey<String>(key)
            it[prefKey] = json.encodeToString(values.toString())
        }
    }

    override suspend fun <T> getList(key: String, default: List<T>): Flow<List<T>> {
        val prefKey = preferencesKey<String>(key)
        return dataStore.data.map {
            val temp = it[prefKey] ?: ""
            json.decodeFromString(temp)
        }
    }
}
