package io.writerme.app.data.work

import android.content.Context
import androidx.core.graphics.drawable.toBitmap
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import coil.ImageLoader
import coil.request.ImageRequest
import io.realm.kotlin.Realm
import io.writerme.app.data.model.Component
import io.writerme.app.net.MetaTagScraper
import io.writerme.app.utils.getDefaultInstance
import io.writerme.app.utils.toFile

class ImageLoadingWorker(
    private val context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    private val componentId = inputData.getLong(IMAGE_COMPONENT_ID, -1)

    override suspend fun doWork(): Result {
        val realm = Realm.getDefaultInstance()

        if (componentId >= 0) {

            val component = realm.query(Component::class, "id == $0", componentId).first().find()

            if (component != null) {
                val metaTags = MetaTagScraper().scrape(component.url)

                val imageUrl = metaTags.ogImage ?: metaTags.twitterImage

                imageUrl?.let { url ->
                    val imageLoader = ImageLoader(context)
                    val request = ImageRequest.Builder(context)
                        .data(url)
                        .build()
                    val result = imageLoader.execute(request).drawable

                    result?.let { drawable ->
                        val bitmap = drawable.toBitmap()
                        val uri = bitmap.toFile(context.filesDir)

                        if (uri != null) {
                            realm.write {
                                findLatest(component)?.mediaUrl = uri.toString()
                            }
                        }
                    }
                }

                realm.close()
                return Result.success()
            }
        }

        realm.close()
        return Result.failure()
    }

    companion object {
        const val IMAGE_COMPONENT_ID = "ici"
    }
}