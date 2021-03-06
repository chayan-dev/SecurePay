package com.example.securepay

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.securepay.databinding.ActivityMainBinding
import com.example.securepay.repository.PicIdentifierRepository
import com.example.securepay.viewmodel.PicIdentifierViewModel
import com.example.securepay.viewmodel.PicIdentifierViewModelFactory
import com.google.android.material.navigation.NavigationView
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: PicIdentifierViewModel

    private val TAG = "MAIN_TAG"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.loginNavHostFragment) as NavHostFragment

        val sharedPrefCam = getSharedPreferences("spCam", Context.MODE_PRIVATE)

        val sharedPrefDocu = getSharedPreferences("spDocu", Context.MODE_PRIVATE)

        viewModel = ViewModelProvider(
            this,
            PicIdentifierViewModelFactory(PicIdentifierRepository())
        ).get(PicIdentifierViewModel::class.java)

    }

}
