package com.popcorn.helpers

import android.widget.ImageView
import coil.api.load
import coil.transform.BlurTransformation
import coil.transform.CircleCropTransformation
import coil.transform.GrayscaleTransformation
import coil.transform.RoundedCornersTransformation
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

class CoilImageLoader @Inject constructor(
    val repository: ImageConfigRepository
) : ImageLoader {

    override fun loadImage(view: ImageView, url: String) {
        view.load(url) {
            crossfade(1000)
        }
    }

    override fun loadCircularImage(view: ImageView, fileName: String) {
        loader(fileName) {
            view.load(it) {
                crossfade(1000)
                transformations(CircleCropTransformation())
            }
        }
    }

    override fun loadBlurImage(
        view: ImageView,
        url: String,
        radius: Float,
        sampling: Float
    ) {
        view.load(url) {
            crossfade(1000)
            transformations(BlurTransformation(view.context, radius, sampling))
        }
    }

    override fun loadRoundedCornersImage(view: ImageView, url: String, radius: Float) {
        view.load(url) {
            crossfade(1000)
            transformations(RoundedCornersTransformation(radius))
        }
    }

    override fun loadGreyScaleImage(view: ImageView, url: String) {
        view.load(url) {
            crossfade(1000)
            transformations(GrayscaleTransformation())
        }
    }

    private fun loader(
        fileName: String,
        url: (String) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val baseUrl = repository.getBaseUrl()
            baseUrl
                .zip(repository.getBackDrop()) { url, images -> "$url${images[images.size - 1]}$fileName" }
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
