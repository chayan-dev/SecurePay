package com.example.securepay.data.api

import com.example.securepay.model.PicIdentifierReqBody
import com.example.securepay.model.PicIdentifierResponse
import com.example.securepay.model.ResponseItem
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface PicIdentifyService {

    @Headers("Ocp-Apim-Subscription-Key: 43efa1fc9ab34feeaa584265a9488bf7")
    @POST("detect?returnFaceAttributes=age,gender&recognitionModel=recognition_01&detectionModel=detection_01&returnFaceId=True")
    fun createPostReq(@Body picIdentifierReqBody: PicIdentifierReqBody): Call<List<ResponseItem>>
}