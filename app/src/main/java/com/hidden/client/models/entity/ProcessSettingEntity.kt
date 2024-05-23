package com.hidden.client.models.entity

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "ProcessSetting")
data class ProcessSettingEntity (

    @field:PrimaryKey(autoGenerate = true)
    var id: Int,

    var candidateFullName: String,

    var jobTitle: String,

    var cityName: String,

    var candidateAvatar: String,

    var isUserManager: Boolean,

    var processId: Int,

    var myId: Int
) {
    @Ignore
    private var userManagerList: ArrayList<ReviewerEntity> = arrayListOf()

    @Ignore
    private var interviewerList: ArrayList<ReviewerEntity> = arrayListOf()

    @Ignore
    private var interviewAdvancerList: ArrayList<ReviewerEntity> = arrayListOf()

    @Ignore
    private var offerManagerList: ArrayList<ReviewerEntity> = arrayListOf()

    fun getUserManagerList(): ArrayList<ReviewerEntity> {
        return this.userManagerList
    }

    fun setUserManagerList(userManagerList: ArrayList<ReviewerEntity>) {
        this.userManagerList = userManagerList
    }

    fun getInterviewerList(): ArrayList<ReviewerEntity> {
        return this.interviewerList
    }

    fun setInterviewerList(interviewerList: ArrayList<ReviewerEntity>) {
        this.interviewerList = interviewerList
    }

    fun getInterviewAdvancerList(): ArrayList<ReviewerEntity> {
        return this.interviewAdvancerList
    }

    fun setInterviewAdvancerList(interviewAdvancerList: ArrayList<ReviewerEntity>) {
        this.interviewAdvancerList = interviewAdvancerList
    }

    fun getOfferManagerList(): ArrayList<ReviewerEntity> {
        return offerManagerList
    }

    fun setOfferManagerList(offerManagerList: ArrayList<ReviewerEntity>) {
        this.offerManagerList = offerManagerList
    }
}
