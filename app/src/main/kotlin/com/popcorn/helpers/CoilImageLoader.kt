package com.popcorn.helpers

import android.widget.ImageView
import coil.load
import coil.transform.CircleCropTransformation
import com.popcorn.domain.repository.ImageConfigRepository
import com.popcorn.utilities.ImageLoader
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

// https://image.tmdb.org/t/p/original${fileName}

class CoilImageLoader @Inject constructor(
    val repository: ImageConfigRepository
) : ImageLoader {

    override fun load(
        view: ImageView,
        filePath: String,
        type: ImageType,
        transformationType: TransformationType
    ) {
        makeConfig(filePath, type) { url ->
            view.load(url) {
                crossfade(1000)
                when (transformationType) {
                    TransformationType.Circle -> transformations(CircleCropTransformation())
                }
            }
        }
    }

    private fun makeConfig(
        fileName: String,
        type: ImageType,
        url: (String) -> Unit,
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val baseUrl = repository.getBaseUrl()
            val imageType = when (type) {
                ImageType.Poster -> repository.getPosters()
                ImageType.BackDrop -> repository.getBackDrop()
                ImageType.Logo -> repository.getLogo()
            }
            baseUrl
                .zip(imageType) { url, image -> "$url${image}$fileName" }
                .catch {
                    it.printStackTrace()
                }
                .collect {
                    withContext(Dispatchers.Main) {
                        url.invoke(it)
                    }
                }
        }
    }
}

sealed class TransformationType {
    object Normal : TransformationType()
    object Circle : TransformationType()
}

sealed class ImageType {
    object Logo : ImageType()
    object BackDrop : ImageType()
    object Poster : ImageType()
}
