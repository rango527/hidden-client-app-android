package com.hidden.client.ui.activities.settings

import android.os.Bundle
import android.view.View
import android.widget.MediaController
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.hidden.client.R
import com.hidden.client.helpers.extension.safeValue
import kotlinx.android.synthetic.main.activity_video_player.*

class VideoPlayerActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var movieUrl: String
    private lateinit var mediaController: MediaController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_player)

        movieUrl = intent.getStringExtra("movieUrl").safeValue()

        val thumbUrl: String = movieUrl.substring(0, movieUrl.length - 3) + "jpg"
        Glide.with(this).load(thumbUrl).into(image_background)

        val txtClose: TextView = findViewById(R.id.text_close)
        txtClose.setOnClickListener(this)

        fnPlayVideo()
    }

    private fun fnPlayVideo() {
        videoview.setVideoPath(movieUrl)
        videoview.start()
        mediaController = MediaController(this)
        mediaController.setAnchorView(videoview)
        videoview.setMediaController(mediaController)

        videoview.setOnPreparedListener {
            progressbar.visibility = View.GONE
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.text_close -> {
                finish()
            }
        }
    }
}
