package com.hidden.client.models.custom

import com.hidden.client.models.entity.ReviewerEntity

data class RoleAvailableUser (
    val user: ReviewerEntity,
    var tick: Boolean,
    var show: Boolean
)