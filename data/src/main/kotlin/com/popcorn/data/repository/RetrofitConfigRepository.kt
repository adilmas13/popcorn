package com.popcorn.data.repository

import com.popcorn.data.entities.ImageConfigResponse
import com.popcorn.data.repository.base.BaseRestApiRepository
import com.popcorn.data.retrofit.ApiService
import com.popcorn.domain.repository.ConfigRepository
import com.popcorn.domain.repository.ImageConfigRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RetrofitConfigRepository @Inject constructor(
    private val api: ApiService,
    private val imageStorage: ImageConfigRepository
) : ConfigRepository,
    BaseRestApiRepository() {

    override suspend fun getConfig(): Flow<Unit> {
        return parseResult(api.getConfig()) { it }.map { storeImageConfig(it.imageConfig) }
    }

    private suspend fun storeImageConfig(response: ImageConfigResponse) {
        imageStorage.storeBaseUrl(response.baseUrl)
        imageStorage.storeLogo(response.logoSizes)
        imageStorage.storeBackDrop(response.backdropSizes)
        imageStorage.storePosters(response.posterSizes)
    }
}
