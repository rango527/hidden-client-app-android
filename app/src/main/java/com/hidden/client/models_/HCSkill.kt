package com.hidden.client.models_

class HCSkill(skill: String, proficiency: Int) {

    private lateinit var skill: String
    private var proficiency: Int = 0

    init {
        setSkill(skill)
        setProficiency(proficiency)
    }

    fun getSkill() : String {
        return skill
    }

    fun setSkill(skill: String) {
        this.skill = skill
    }

    fun getProficiency() : Int {
        return proficiency
    }

    fun setProficiency(proficiency: Int) {
        this.proficiency = proficiency
    }
}