package com.example.securepay

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.securepay.databinding.ActivityHomeBinding
import com.example.securepay.databinding.ActivityVerifyBinding
import com.google.firebase.auth.FirebaseAuth

class Home:AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    private lateinit var firebaseAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        var currentUser = firebaseAuth.currentUser

        if (currentUser == null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        binding.idLogout.setOnClickListener {
            firebaseAuth.signOut()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}