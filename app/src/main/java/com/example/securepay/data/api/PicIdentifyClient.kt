package com.example.securepay.data.api

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object PicIdentifyClient {
    private val gson=
        GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create()
    private val retrofit= Retrofit.Builder()
        .baseUrl("https://securepayfaceapiinstance.cognitiveservices.azure.com/face/v1.0/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
    val api= retrofit.create(PicIdentifyService::class.java)
}