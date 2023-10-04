package io.writerme.app.data.work

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import io.realm.kotlin.Realm
import io.writerme.app.data.model.Component
import io.writerme.app.net.MetaTagScraper
import io.writerme.app.utils.FilesUtil
import io.writerme.app.utils.getDefaultInstance

class ImageLoadingWorker(
    private val context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    private val componentId = inputData.getLong(IMAGE_COMPONENT_ID, -1)

    override suspend fun doWork(): Result {
        val realm = Realm.getDefaultInstance()

        if (componentId >= 0) {

            val component = realm.query(Component::class, "id == $0", componentId).first().find()

            if (
                component != null
                && ContextCompat.checkSelfPermission(context, Manifest.permission.INTERNET)
                    == PackageManager.PERMISSION_GRANTED
            ) {
                val metaTags = MetaTagScraper().scrape(component.url)

                Log.d("ImageLoadingWorker", "tags: $metaTags")

                val imageUrl = metaTags.ogImage ?: metaTags.twitterImage

                imageUrl?.let { url ->
                    val uri = FilesUtil(context).writeImageToFile(url)

                    uri?.let {
                        realm.write {
                            findLatest(component)?.mediaUrl = it
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