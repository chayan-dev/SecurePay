package com.example.securepay.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.securepay.model.PicIdentifierReqBody
import com.example.securepay.model.ResponseItem
import com.example.securepay.repository.PicIdentifierRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PicIdentifierViewModel(private val repository: PicIdentifierRepository) : ViewModel() {

    /**
     * to get create user api response info
     */
    private val _picIdentifierResponse = MutableLiveData<List<ResponseItem>>(null)
    val picIdentifierResponse: LiveData<List<ResponseItem>> = _picIdentifierResponse

    private val _faceId = MutableLiveData<String>()
    val faceId: LiveData<String> = _faceId


    fun postPicIdentifier(picIdentifierReqBody: PicIdentifierReqBody) {
        val response = repository.getPicIdentifierResponse(picIdentifierReqBody)
        response.enqueue(object : Callback<List<ResponseItem>> {
            override fun onResponse(
                call: Call<List<ResponseItem>>,
                response: Response<List<ResponseItem>>
            ) {
                Log.d("viewBody", response.body().toString())
                _picIdentifierResponse.value = response.body()
                if (response.body()?.isNotEmpty() == true)
                    _faceId.value = response.body()!![0].faceId!!
            }

            override fun onFailure(call: Call<List<ResponseItem>>, t: Throwable) {
                Log.d("ApiErrorChk", t.message.toString())
                // errorMessage.postValue(t.message)
            }
        })
    }

}

