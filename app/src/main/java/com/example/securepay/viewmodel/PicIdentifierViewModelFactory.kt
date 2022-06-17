package com.example.securepay.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.securepay.repository.PicIdentifierRepository

class PicIdentifierViewModelFactory(private val repository: PicIdentifierRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(PicIdentifierViewModel::class.java)) {
            PicIdentifierViewModel(repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}