package io.writerme.app.utils

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.core.graphics.drawable.toBitmap
import coil.ImageLoader
import coil.request.ImageRequest
import coil.size.Size

class FilesUtil(private val context: Context) {

    suspend fun writeImageToFile(url: String) : String? {
        val config = Bitmap.Config.RGB_565

        val imageLoader = ImageLoader.Builder(context).allowRgb565(true).bitmapConfig(config).build()

        val request = ImageRequest.Builder(context)
            .data(url)
            .size(Size.ORIGINAL)
            .crossfade(false)
            .build()

        val drawable = imageLoader.execute(request).drawable

        Log.d("FilesUtil", "width: ${drawable?.intrinsicWidth}, height: ${drawable?.intrinsicHeight}")

        return if (drawable != null) {
            val bitmap = drawable.toBitmap(config = config)

            bitmap.toFile(context.filesDir)?.path
        } else {
            Log.d("FilesUtil", "bitmap is null")
            null
        }
    }
}