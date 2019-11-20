package com.hidden.client.ui.activities.settings

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.MediaController
import com.bumptech.glide.Glide
import com.hidden.client.R
import com.hidden.client.helpers.HCGlobal
import kotlinx.android.synthetic.main.activity_video_player.*

class VideoPlayerActivity : AppCompatActivity() {

    private lateinit var movieUrl: String
    private lateinit var mediaController: MediaController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_player)

        movieUrl = intent.getStringExtra("movieUrl")

        val thumbUrl: String = movieUrl.substring(0, movieUrl.length - 3) + "jpg"
        Glide.with(this).load(thumbUrl).into(image_background)

        fn_playvideo()
    }

    private fun fn_playvideo() {
        videoview.setVideoPath(movieUrl)
        videoview.start()
        mediaController = MediaController(this)
        mediaController.setAnchorView(videoview)
        videoview.setMediaController(mediaController)

        videoview.setOnPreparedListener (MediaPlayer.OnPreparedListener {
            progressbar.visibility = View.GONE
        })
    }
}
