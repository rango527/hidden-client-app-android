package com.hidden.client.models

class Metrics {
    var title: String = ""
        get() = field
        set(value) { field = value }

    var cnt: String = ""
        get() = field
        set(value) { field = value }

    constructor(titles: String, cnts: String) {
        this.title = titles
        this.cnt = cnts
    }
}