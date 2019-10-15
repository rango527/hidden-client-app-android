package com.hidden.client.apis

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import java.io.IOException

//object ErrorUtils {
//
//    fun parseError(response: Response<*>, client: Retrofit): APIError {
//        val converter = client.responseBodyConverter(APIError::class.java, arrayOfNulls<Annotation>(0))
//
//        val error: APIError
//
//        try {
//            error = converter.convert(response.errorBody())
//        } catch (e: IOException) {
//            return APIError()
//        }
//
//        return error
//    }
//}