package io.writerme.app.data.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import coil.ImageLoader
import coil.request.ImageRequest
import io.realm.kotlin.Realm
import io.writerme.app.data.model.Component
import io.writerme.app.net.MetaTagScraper
import io.writerme.app.utils.getDefaultInstance

class ImageLoadingWorker(
    private val context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    private val componentId = inputData.getLong(IMAGE_COMPONENT_ID, -1)

    override suspend fun doWork(): Result {
        val realm = Realm.getDefaultInstance()

        if (componentId >= 0) {

            val component = realm.query(Component::class, "id = $0", componentId).first().find()

            if (component != null) {
                val metaTags = MetaTagScraper().scrape(component.url)

                val imageUrl = metaTags.ogImage ?: metaTags.twitterImage

                imageUrl?.let {
                    val imageLoader = ImageLoader(context)
                    val request = ImageRequest.Builder(context)
                        .data(it)
                        .build()
                    val drawable = imageLoader.execute(request).drawable

                    // TODO: save to file, add file url to image component, write to DB
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