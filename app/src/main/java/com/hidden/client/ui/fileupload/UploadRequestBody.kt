package com.hidden.client.ui.fileupload

import android.os.Looper
import okhttp3.MediaType
import okhttp3.RequestBody
import okio.BufferedSink
import java.io.File
import java.io.FileInputStream
import java.util.logging.Handler

class UploadRequestBody (
    private val attachment: File,
    private val contentType: String,
    private val callback: UploadCallback
) : RequestBody() {

    interface UploadCallback {
        fun onProgressUpdate(percentage: Int)
    }

    inner class ProgressUpdate(
        private val uploaded: Long,
        private val total: Long
    ) : Runnable{

        override fun run() {
            callback.onProgressUpdate((100 * uploaded / total).toInt())
        }
    }
    override fun contentType() = MediaType.parse( "$contentType/*")

    override fun contentLength() = attachment.length()

    override fun writeTo(sink: BufferedSink) {
        val length = attachment.length()
        val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
        val fileInputStream = FileInputStream(attachment)
        var uploaded = 0L

//        fileInputStream.use {inputStream ->
//            var read: Int
//            val handler = Handler(Looper.getMainLooper())
//
//            while (inputStream.read(buffer).also { read = it } != -1) {
//                handler.post(ProgressUpdate(uploaded, length))
//                uploaded += read
//                sink.write(buffer, 0, read)
//            }
//        }
    }

    companion object {
        private const val DEFAULT_BUFFER_SIZE = 1048
    }
}