package io.writerme.app.utils

import android.content.Context
import androidx.core.graphics.drawable.toBitmap
import coil.ImageLoader
import coil.request.ImageRequest

class FilesUtil(private val context: Context) {

    suspend fun writeImageToFile(url: String) : String? {
        val imageLoader = ImageLoader(context)
        val request = ImageRequest.Builder(context)
            .data(url)
            .build()

        val drawable = imageLoader.execute(request).drawable

        return if (drawable != null) {
            val bitmap = drawable.toBitmap()
            bitmap.toFile(context.filesDir)?.path
        } else null
    }
}