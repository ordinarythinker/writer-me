package io.writerme.app.data.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class BookmarkImageLoadingWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        // TODO

        return Result.success()
    }

}