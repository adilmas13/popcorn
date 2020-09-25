package com.popcorn.data.repository

import com.popcorn.data.repository.base.BaseRestApiRepository
import com.popcorn.data.retrofit.ApiService
import com.popcorn.domain.repository.ConfigRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RetrofitConfigRepository @Inject constructor(
    private val api: ApiService
) : ConfigRepository,
    BaseRestApiRepository() {
    override suspend fun getConfig(): Flow<Boolean> {
        return parseResult(api.getConfig()) { true }
    }
}
