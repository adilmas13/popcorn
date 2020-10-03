package com.popcorn.utilities

import android.widget.ImageView
import com.popcorn.helpers.ImageType
import com.popcorn.helpers.TransformationType

interface ImageLoader {

    fun load(
        view: ImageView,
        filePath: String,
        type: ImageType = ImageType.Poster,
        transformationType: TransformationType = TransformationType.Normal
    )
}
