package com.hidden.client.models

class HCSkill {

    private lateinit var skill: String
    private var percent: Int = 0

    constructor(skill: String, percent: Int) {
        setSkill(skill)
        setPercent(percent)
    }

    fun getSkill() : String {
        return skill
    }

    fun setSkill(skill: String) {
        this.skill = skill
    }

    fun getPercent() : Int {
        return percent
    }

    fun setPercent(percent: Int) {
        this.percent = percent
    }
}