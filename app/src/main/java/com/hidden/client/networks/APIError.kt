package com.hidden.client.networks

class APIError {

    private val errors: Array<String> = arrayOf()
    private val stat: Int = 0

    fun errors(): Array<String> {
        return errors
    }

    fun stat(): Int {
        return stat
    }

}