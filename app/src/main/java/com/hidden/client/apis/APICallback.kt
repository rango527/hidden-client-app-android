package com.hidden.client.apis

internal interface APICallbackInterface<T> {
    //    void onCompleted(List<T> results, String errMsg);
    fun onCompleted(result: T, errMsg: String)

    fun onCompleted(errMsg: String)
    fun onProgress(msg: String)
}

abstract class APICallback<T> : APICallbackInterface<T> {

    override fun onCompleted(errMsg: String) {

    }

    override fun onCompleted(result: T, errMsg: String) {

    }

    override fun onProgress(msg: String) {

    }
}