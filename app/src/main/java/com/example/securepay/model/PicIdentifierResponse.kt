package com.example.securepay.model

import com.google.gson.annotations.SerializedName


	@field:SerializedName("Response")
	val picIdResponse: List<ResponseItem?>? = null

data class ResponseItem(

	@field:SerializedName("faceRectangle")
	val faceRectangle: FaceRectangle? = null,

	@field:SerializedName("faceAttributes")
	val faceAttributes: FaceAttributes? = null,

	@field:SerializedName("faceId")
	val faceId: String? = null
)

data class FaceRectangle(

	@field:SerializedName("top")
	val top: Int? = null,

	@field:SerializedName("left")
	val left: Int? = null,

	@field:SerializedName("width")
	val width: Int? = null,

	@field:SerializedName("height")
	val height: Int? = null
)

data class FaceAttributes(

	@field:SerializedName("gender")
	val gender: String? = null,

	@field:SerializedName("age")
	val age: Double? = null
)
