package com.example.securepay.repository

import com.example.securepay.data.api.PicIdentifyClient
import com.example.securepay.model.PicIdentifierReqBody

class PicIdentifierRepository {
    fun getPicIdentifierResponse(picIdentifierReqBody: PicIdentifierReqBody) = PicIdentifyClient.api.createPostReq(picIdentifierReqBody)
}