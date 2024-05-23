package com.hidden.client.datamodels

data class HCFeedbackResponse(
    val feedback__feedback_id: Int,
    val feedback__questions: List<HCFeedbackQuestionResponse>
)