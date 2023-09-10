package io.writerme.app.data.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import coil.ImageLoader
import coil.request.ImageRequest

class BookmarkImageLoadingWorker(
    private val context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        // TODO: define html properties, get Bitmap, save to file, add file url to image component

        // https://github.com/coil-kt/coil
        val imageLoader = ImageLoader(context)
        val request = ImageRequest.Builder(context)
            .data("https://example.com/image.jpg")
            .build()
        val drawable = imageLoader.execute(request).drawable

        return Result.success()
    }

    companion object {
        const val IMAGE_COMPONENT_ID = "ici"
    }
}