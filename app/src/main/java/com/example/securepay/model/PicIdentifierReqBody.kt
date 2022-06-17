package com.example.securepay.model

import com.google.gson.annotations.SerializedName

data class PicIdentifierReqBody(

	@field:SerializedName("url")
	val url: String? = null
)
