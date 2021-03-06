package com.popcorn.data.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConfigWrapperResponse(
    @SerialName("images") val imageConfig: ImageConfigResponse
)

@Serializable
data class ImageConfigResponse(
    @SerialName("secure_base_url") val baseUrl: String,
    @SerialName("backdrop_sizes") val backdropSizes: List<String>,
    @SerialName("logo_sizes") val logoSizes: List<String>,
    @SerialName("poster_sizes") val posterSizes: List<String>,
    @SerialName("profile_sizes") val profileSizes: List<String>,
    @SerialName("still_sizes") val stillSizes: List<String>,
)
