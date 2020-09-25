package com.popcorn.domain.usecase

import com.popcorn.domain.base.UseCase
import com.popcorn.domain.repository.ConfigRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ConfigUseCase @Inject constructor(
    private val repository: ConfigRepository
) : UseCase<Boolean>() {

    override suspend fun makeRequest() = repository.getConfig()

    suspend fun getConfig(): Flow<Boolean> {
        return execute()
    }
}
