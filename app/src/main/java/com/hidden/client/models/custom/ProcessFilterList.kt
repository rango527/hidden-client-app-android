package com.hidden.client.models.custom

class ProcessFilterList {
    var currentReadStatus: Int = -1             //-1:all, 0: has unread message, 1: has no unread message
    var currentSortBy: Int = -1                 //-1: all, 0: most recent activity, 1: process stage, 2: shortlisting date
    var currentShortlistStage: Boolean = false
    var currentFirstStage: Boolean = false
    var currentFurtherStage: Boolean = false
    var currentFinalStage: Boolean = false
    var currentOfferStage: Boolean = false
    var currentOfferAccepted: Boolean = false
    var currentStarted: Boolean = false
}