package com.hidden.client.ui.activities.process

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.lifecycle.ViewModelProviders
import com.google.gson.JsonObject
import com.hidden.client.R
import com.hidden.client.ui.BaseActivity
import com.hidden.client.ui.activities.ProcessActivity
import com.hidden.client.ui.viewmodels.injection.ViewModelFactory
import com.hidden.client.ui.viewmodels.main.GiveFeedbackVM
import okhttp3.MediaType
import okhttp3.RequestBody

class HSGiveAvailabilityActivity : BaseActivity() {

    private var processId: Int = 0

    private lateinit var editComment: EditText
    private lateinit var layoutSubmitMessage: LinearLayout
    private lateinit var imageClose: ImageButton

    private lateinit var giveFeedbackViewModel: GiveFeedbackVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_give_availability)

        processId = intent.getIntExtra("processId", 0)

        giveFeedbackViewModel =
            ViewModelProviders.of(this, ViewModelFactory(this)).get(GiveFeedbackVM::class.java)

        editComment = findViewById(R.id.edit_comment)

        imageClose = findViewById(R.id.image_close)
        imageClose.setOnClickListener {
            backToProcess(true)
        }

        layoutSubmitMessage = findViewById(R.id.layout_submit_message)
        layoutSubmitMessage.setOnClickListener {
            submitFeedback(editComment.text.toString())
        }
    }

    private fun submitFeedback(comment: String) {
        val body = JsonObject()
        body.addProperty("message", comment)

        giveFeedbackViewModel.submitInterviewProposedDates(processId, RequestBody.create(MediaType.parse("application/json"), body.toString()))
        backToProcess(false)
    }

    private fun backToProcess(cashMode: Boolean) {
        val intent = Intent(this, ProcessActivity::class.java)
        intent.putExtra("processId", processId)
        intent.putExtra("cashMode", cashMode)
        startActivity(intent)
        overridePendingVTransitionEnter()
        finish()
    }
}


