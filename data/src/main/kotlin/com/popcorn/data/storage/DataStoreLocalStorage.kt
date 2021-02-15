package com.popcorn.data.storage

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.popcorn.domain.LocalStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreLocalStorage @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : LocalStorage {

    override suspend fun setString(key: String, value: String) {
        dataStore.edit {
            val prefKey = stringPreferencesKey(key)
            it[prefKey] = value
        }
    }

    override suspend fun getString(key: String, default: String): Flow<String> {
        val prefKey = stringPreferencesKey(key)
        return dataStore.data.map { it[prefKey] ?: default }
    }
}
