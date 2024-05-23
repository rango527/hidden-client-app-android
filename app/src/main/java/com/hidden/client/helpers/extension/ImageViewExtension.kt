package com.hidden.client.helpers.extension

import android.media.MediaMetadataRetriever
import android.os.Build
import android.graphics.Bitmap
import android.widget.ImageView


@Throws(Throwable::class)
fun ImageView.setImageBitmapFromVideoThumbnail(videoPath: String) {
    var bitmap: Bitmap?
    var mediaMetadataRetriever: MediaMetadataRetriever? = null
    try {
        mediaMetadataRetriever = MediaMetadataRetriever()
        if (Build.VERSION.SDK_INT >= 14)
            mediaMetadataRetriever.setDataSource(videoPath, HashMap())
        else
            mediaMetadataRetriever.setDataSource(videoPath)

        bitmap = mediaMetadataRetriever.getFrameAtTime(1, MediaMetadataRetriever.OPTION_CLOSEST)


        if (bitmap != null) {
            bitmap = Bitmap.createScaledBitmap(bitmap, 240, 240, false);
            this.setImageBitmap(bitmap)
        }

    } catch (e: Exception) {
        e.printStackTrace()
        throw Throwable(
            "Exception in retriveVideoFrameFromVideo(String videoPath)" + e.message
        )

    } finally {
        mediaMetadataRetriever?.release()
    }
}