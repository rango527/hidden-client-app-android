package com.hidden.client.ui.activities

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.hidden.client.R
import com.hidden.client.helpers.HCDate
import com.hidden.client.helpers.HCGlobal
import com.hidden.client.helpers.extension.safeValue

class ConversationImageShowActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var buttionClose: TextView
    private lateinit var imageItem: ImageView
    private lateinit var videoItem: VideoView
    private lateinit var btnPlay: ImageView
    private lateinit var showImageCreateDate: TextView
    private lateinit var showImageBackground: ConstraintLayout
    private lateinit var navFooterView: TextView

    private var isOneClick: Boolean = true

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_message_image_file)

        val messageId = intent.getIntExtra("messageId", 0)
        val imageList = HCGlobal.getInstance().imageMessageList

        for (image in HCGlobal.getInstance().imageMessageList) {
            if (messageId == image.messageId) {

                buttionClose = findViewById(R.id.text_image_close)
                buttionClose.setOnClickListener(this)

                showImageCreateDate = findViewById(R.id.show_image_create_date)
                val fromDate = HCDate.stringToDate(image.messageTime, null)
                val strDate: String
                strDate = HCDate.dateToString(fromDate!!, "MMM d, yyyy' at 'hh:mm a").toString()
                showImageCreateDate.text = strDate

                navFooterView = findViewById(R.id.nav_footer_view)
                navFooterView.text = image.id.toString() + "/" + HCGlobal.getInstance().imageMessageList.size
                showImageBackground = findViewById(R.id.show_image_background)
                imageItem = findViewById(R.id.image_item)
                videoItem = findViewById(R.id.video_item)
                btnPlay = findViewById(R.id.play_button)

                val imageView = ImageView(this)

                if (image.messageAssetType == "IMAGE") {
                    imageItem.visibility = View.VISIBLE
                    videoItem.visibility = View.GONE
                    btnPlay.visibility = View.GONE

                    showImageBackground.setOnClickListener(this)
//showImageBackground.setBackgroundResource(image.messageUrl)
                    Glide.with(this).load(image.messageUrl).into(imageView)
                    showImageBackground.addView(imageView)

                } else if (image.messageAssetType == "VIDEO") {
                    imageItem.visibility = View.GONE
                    videoItem.visibility = View.VISIBLE
                    btnPlay.visibility = View.VISIBLE

                    val newPhotoUrl = image.messageUrl.safeValue().substring(0, image.messageUrl.safeValue().length - 3) + "jpg"
                    Glide.with(HCGlobal.getInstance().currentActivity).load(newPhotoUrl).into(imageView)
                    showImageBackground.addView(imageView)

                    btnPlay.setOnClickListener {
                        btnPlay.visibility = View.GONE

                        var mediaControls: MediaController? = null

                        if (mediaControls == null) {
                            // creating an object of media controller class
                            mediaControls = MediaController(this)

                            // set the anchor view for the video view
                            mediaControls.setAnchorView(this.videoItem)
                        }

                        // set the media controller for video view
                        videoItem.setMediaController(mediaControls)

                        // set the absolute path of the video file which is going to be played
                        videoItem.setVideoURI(
                            Uri.parse(image.messageUrl)
                        )

                        videoItem.requestFocus()

                        // starting the video
                        videoItem.start()

                        // display a toast message
                        // after the video is completed
                        videoItem.setOnCompletionListener {
                            Toast.makeText(
                                applicationContext, "Video completed",
                                Toast.LENGTH_LONG
                            ).show()
                            btnPlay.visibility = View.VISIBLE
                        }

                        // display a toast message if any
                        // error occurs while playing the video
                        videoItem.setOnErrorListener { mp, what, extra ->
                            Toast.makeText(
                                applicationContext, "An Error Occured " +
                                        "While Playing Video !!!", Toast.LENGTH_LONG
                            ).show()
                            false
                        }
                    }
                }
            }
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.text_image_close -> {
                finish()
            }

            R.id.show_image_background -> {
                if (isOneClick) {
                    buttionClose.visibility = View.GONE
                    showImageCreateDate.visibility = View.GONE
                    navFooterView.visibility = View.GONE
                } else {
                    buttionClose.visibility = View.VISIBLE
                    showImageCreateDate.visibility = View.VISIBLE
                    navFooterView.visibility = View.VISIBLE
                }
                isOneClick = !isOneClick
            }

        }
    }
}
